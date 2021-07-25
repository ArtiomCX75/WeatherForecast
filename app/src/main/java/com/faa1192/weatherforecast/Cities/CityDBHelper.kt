package com.faa1192.weatherforecast.cities

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.faa1192.weatherforecast.DBHelper
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.TABLE_LIST_CITY_NAME
import com.faa1192.weatherforecast.countries.CountriesActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.util.*

//хелпер для работы с базой всех городов
class CityDBHelper protected constructor(context: Context?) : DBHelper(context!!) {
    private fun getCursorC(query: String): Cursor {
        return this.writableDatabase.rawQuery(
            "select * from (select * from (select * from " + TABLE_LIST_CITY_NAME + " where Name like 'А%' or Name like 'Б%' or Name like 'В%' or Name like 'Г%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Ґ%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Д%' or Name like 'Е%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Є%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Ж%' or Name like 'З%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'И%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'І%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Ї%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Й' or Name like 'К%' or Name like 'Л%' or Name like 'М%' or Name like 'Н%' or Name like 'О%' or Name like 'П%' or Name like 'Р%' or Name like 'С%' or Name like 'Т%' or Name like 'У%' or Name like 'Ф%' or Name like 'Х%' or Name like 'Ц%' or Name like 'Ч%' or Name like 'Ш%' or Name like 'Щ%' or Name like 'Ъ%' or Name like 'Ы%' or Name like 'Ь%' or Name like 'Э%' or Name like 'Ю%' or Name like 'Я%' order by Name )) where Name like '%" + query + "%' ",
            null
        )
    }

    fun getCityList(query: String): List<City> {
        val cursor = getCursorC(query)
        val cityList: MutableList<City> = ArrayList()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val country = cursor.getString(2)
            val lon = cursor.getString(3)
            val lat = cursor.getString(4)
            cityList.add(City(id, name, country, lon, lat))
        }
        cursor.close()
        return cityList
    }

    fun downloadCountry(str: String?) {
        val coc = CitiesOfCountry()
        coc.execute(str)
    }

    //Загрузка городов с инета
    private inner class CitiesOfCountry : AsyncTask<String?, Int?, Void?>() {
        var countryName = ""
        val list = ArrayList<String>()
        var success = false
        override fun onPreExecute() {
            super.onPreExecute()
            (context as Activity).findViewById<View>(R.id.progressBar).visibility =
                View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): Void? {
            success = false
            countryName = params[0].toString()
            val urlString =
                "https://raw.githubusercontent.com/ArtiomCX75/WeatherForecast/develop/citieslist/$countryName.txt"
            Log.e("my", "url:$urlString")
            try {
                val client = OkHttpClient()
                val request: Request = Request.Builder().url(urlString).build()
                val response = client.newCall(request).execute()
                val br = BufferedReader(response.body!!.charStream())
                var temp = br.readLine()
                while (temp != null && !temp.isEmpty()) {
                    list.add(temp)
                    temp = br.readLine()
                }
                success = true
            } catch (e: Exception) {
                var i = 0
                while (i < e.stackTrace.size) {
                    Log.e("my", e.stackTrace[i].toString())
                    i++
                }
            }
            if (success) {
                try {
                    this@CityDBHelper.writableDatabase.execSQL("DELETE from " + TABLE_LIST_CITY_NAME + " WHERE country = '" + countryName + "'")
                } catch (e: SQLException) {
                    Log.e("my", "sql exception. db doesn't exist")
                }
                try {
                    Log.e("my", "success")
                    val db = this@CityDBHelper.writableDatabase
                    val contentValues = ContentValues()
                    var city: City
                    db.beginTransaction()
                    for (i in list.indices) {
                        city = City(list[i])
                        contentValues.put("_id", city.id)
                        contentValues.put("name", city.name)
                        contentValues.put("country", city.country)
                        contentValues.put("lon", city.lon)
                        contentValues.put("lat", city.lat)
                        db.insert(TABLE_LIST_CITY_NAME, null, contentValues)
                        publishProgress(i * 100 / list.size)
                    }
                    db.setTransactionSuccessful()
                    db.endTransaction()
                    //    Toast.makeText(context, "download completed", Toast.LENGTH_LONG).show();
                    Log.e("my", "add " + list.size)
                } catch (e: Exception) {
                    Log.e(
                        "my",
                        "citydbhelper: cannot be cast to updatable or sql exeption"
                    ) // не критично
                    var i = 0
                    while (i < e.stackTrace.size) {
                        Log.e("my", e.stackTrace[i].toString())
                        i++
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.connection_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            ((context as Activity).findViewById<View>(R.id.progressBar) as ProgressBar).progress =
                values[0]!!
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            /*((Activity) context).findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            Intent intent = new Intent(context, PrefCitiesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            */(context as Activity).finish()
            (context as CountriesActivity).overridePendingTransition(
                R.anim.alpha_on,
                R.anim.alpha_off
            )
        }


    }

    companion object {
        //  private static final String DB_NAME = "city_db";
        //   private static final String TABLE_NAME = "CITY";
        //  private static final int DB_VERSION = 2;
        //   private final Context context;
        // SQLiteDatabase db;
        //   private CityDBHelper(Context context) {
        //       super(context, DB_NAME, null, DB_VERSION);
        //       this.context = context;
        //   }
        @JvmStatic
        fun init(context: Context?): CityDBHelper {
            return CityDBHelper(context)
        }
    }
}