package com.faa1192.weatherforecast.preferred

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.faa1192.weatherforecast.DBHelper
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.TABLE_PREF_NAME
import com.faa1192.weatherforecast.Updatable
import com.faa1192.weatherforecast.cities.City
import com.faa1192.weatherforecast.weather.WeatherData
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.IOException

//хелпер для работы с базой городов добавленных в избранное
class PrefCityDBHelper private constructor(context: Context) : DBHelper(context) {
    private val cursorPC: Cursor
        get() = this.writableDatabase.query(
            TABLE_PREF_NAME,
            arrayOf("_id", "NAME", "country", "lon", "lat", "DATA"),
            null,
            null,
            null,
            null,
            "Name"
        )

    //Получение города из избранного по ид
    fun getCity(id: Int): City {
        val cursor = this.writableDatabase.query(
            TABLE_PREF_NAME,
            arrayOf("_id", "NAME", "country", "lon", "lat", "DATA"),
            "_id=$id",
            null,
            null,
            null,
            null
        )
        cursor.moveToFirst()
        val _id = cursor.getInt(0)
        val name = cursor.getString(1)
        val country = cursor.getString(2)
        val lon = cursor.getString(3)
        val lat = cursor.getString(4)
        val weatherData = WeatherData(cursor.getString(5))
        cursor.close()
        return City(_id, name, country, lon, lat, weatherData)
    }

    //Получение списка городов из избранного
    val cityList: List<City>
        get() {
            val cursor = cursorPC
            val cityList: MutableList<City> = ArrayList()
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val country = cursor.getString(2)
                val lon = cursor.getString(3)
                val lat = cursor.getString(4)
                val weatherData = WeatherData(cursor.getString(5))
                cityList.add(City(id, name, country, lon, lat, weatherData))
            }
            cursor.close()
            return cityList
        }

    //Обновление данных о погоде в городах из избранного
    fun updateAllDataFromWeb() {
        val cursor = PrefCityDBHelper(context).cursorPC
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val name = cursor.getString(1)
            val country = cursor.getString(2)
            val lon = cursor.getString(3)
            val lat = cursor.getString(4)
            val weatherData = WeatherData(cursor.getString(5))
            val city = City(id, name, country, lon, lat, weatherData)
            updateDataFromWeb(city)
        }
        cursor.close()
    }

    //Загрузка данных о погоде с инета, если данные неактуальны
    fun updateDataFromWeb(city: City) {
        if (city.data.isActualData) {
            //Toast.makeText(context, "actual", Toast.LENGTH_SHORT).show(); //for debug
            Log.e("my", "actual")
            return
        } else {
            //Toast.makeText(context, "old", Toast.LENGTH_SHORT).show(); //for debug
            Log.e("my", "old")
        }
        val webUpdateHelper = WebUpdateHelper()
        webUpdateHelper.execute(city)
    }

    //удаление города из избранного
    fun delFromDbPref(city: City) {
        try {
            writableDatabase.delete(TABLE_PREF_NAME, "_id=" + city.id, null)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    //добавление города в избранное
    fun addToDbPref(city: City) {
        try {
            val contentValues = ContentValues()
            contentValues.put("_id", city.id)
            contentValues.put("name", city.name)
            contentValues.put("country", city.country)
            contentValues.put("lon", city.lon)
            contentValues.put("lat", city.lat)
            contentValues.put("DATA", city.data.jsonString)
            writableDatabase.insert(TABLE_PREF_NAME, null, contentValues)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    //класс для работы с инетом
    private inner class WebUpdateHelper : AsyncTask<City?, Void?, Void?>() {
        var resultString = ""

        //  InputStream inputStream;
        var city: City? = null
        var success = false

        //(vararg params: City?): Void?
        override fun doInBackground(vararg city: City?): Void? {
            success = false
            this.city = city[0]
            val urlString =
                "http://api.openweathermap.org/data/2.5/weather?id=" + city[0]?.id + "&appid=5fa682315be7b0b6b329bca80a9bbf08&lang=en&units=metric"
            Log.e("my", "url:$urlString")
            try {
                Log.d("my", "1")
                val client = OkHttpClient()
                Log.d("my", "2")
                val request = Request.Builder().url(urlString).build()
                Log.d("my", "3")
                val response = client.newCall(request).execute()
                Log.d("my", "4")
                val br = BufferedReader(response.body!!.charStream())
                Log.d("my", "5")
                resultString = br.readLine()
                Log.d("my", "6")
                success = true
                Log.d("my", "7")
            } catch (e: IOException) {
                Log.e("my", "Проблемы с загрузкой")
                var i = 0
                while (i < e.stackTrace.size) {
                    Log.e("my", e.stackTrace[i].toString())
                    i++
                }
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            try {
                if (success) {
                    val weatherData = WeatherData(resultString)
                    city!!.data = WeatherData(resultString)
                    val contentValues = ContentValues()
                    contentValues.put("DATA", weatherData.jsonString)
                    dbHelper.writableDatabase.update(
                        TABLE_PREF_NAME,
                        contentValues,
                        "_id = " + city!!.id,
                        null
                    )
                    //    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show(); //for debug
                    (context as Updatable).update()
                } else {
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.connection_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: SQLException) {
                Log.e("my", "sql exception")
                var i = 0
                while (i < e.stackTrace.size) {
                    Log.e("my", e.stackTrace[i].toString())
                    i++
                }
            } catch (e: Exception) {
                Log.e("my", "prefcitydbhelper: cannot be cast to updatable") // не критично
                var i = 0
                while (i < e.stackTrace.size) {
                    Log.e("my", e.stackTrace[i].toString())
                    i++
                }
            }
        }
    }

    companion object {
        fun customInit(context: Context): PrefCityDBHelper {
            return PrefCityDBHelper(context)
        }
    }

}