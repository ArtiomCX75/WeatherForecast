package com.faa1192.weatherforecast.Cities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.faa1192.weatherforecast.Countries.CountriesActivity;
import com.faa1192.weatherforecast.DBHelper;
import com.faa1192.weatherforecast.Preferred.PrefCitiesActivity;
import com.faa1192.weatherforecast.R;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//хелпер для работы с базой всех городов
public class CityDBHelper extends DBHelper {
    protected CityDBHelper(Context context) {
        super(context);
    }
    //  private static final String DB_NAME = "city_db";
 //   private static final String TABLE_NAME = "CITY";

  //  private static final int DB_VERSION = 2;
 //   private final Context context;

    // SQLiteDatabase db;
 //   private CityDBHelper(Context context) {
 //       super(context, DB_NAME, null, DB_VERSION);
 //       this.context = context;
 //   }

    public static CityDBHelper init(Context context) {
        return new CityDBHelper(context);
    }

    private Cursor getCursorC(String query) {
        return this.getWritableDatabase().rawQuery(
                "select * from (select * from (select * from " + TABLE_LIST_CITY_NAME + " where Name like 'А%' or Name like 'Б%' or Name like 'В%' or Name like 'Г%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Ґ%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Д%' or Name like 'Е%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Є%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Ж%' or Name like 'З%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'И%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'І%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Ї%' order by Name ) UNION ALL select * from ( select * from " + TABLE_LIST_CITY_NAME + " where Name like 'Й' or Name like 'К%' or Name like 'Л%' or Name like 'М%' or Name like 'Н%' or Name like 'О%' or Name like 'П%' or Name like 'Р%' or Name like 'С%' or Name like 'Т%' or Name like 'У%' or Name like 'Ф%' or Name like 'Х%' or Name like 'Ц%' or Name like 'Ч%' or Name like 'Ш%' or Name like 'Щ%' or Name like 'Ъ%' or Name like 'Ы%' or Name like 'Ь%' or Name like 'Э%' or Name like 'Ю%' or Name like 'Я%' order by Name )) where Name like '%" + query + "%' ", null);
    }

    public List<City> getCityList(String query) {
        Cursor cursor = getCursorC(query);
        List<City> cityList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String country = cursor.getString(2);
            String lon = cursor.getString(3);
            String lat = cursor.getString(4);
            cityList.add(new City(id, name, country, lon, lat));
        }
        cursor.close();
        return cityList;
    }


    public void downloadCountry(String str) {
        CitiesOfCountry coc = new CitiesOfCountry();
        coc.execute(str);
    }

    //Загрузка городов с инета
    private class CitiesOfCountry extends AsyncTask<String, Integer, Void> {
        String countryName = "";
        final ArrayList<String> list = new ArrayList<>();
        boolean success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((Activity) context).findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            success = false;
            countryName = strings[0];
            String urlString = "https://raw.githubusercontent.com/ArtiomCX75/WeatherForecast/develop/citieslist/" + countryName + ".txt";
            Log.e("my", "url:" + urlString);
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(urlString).build();
                Response response = client.newCall(request).execute();
                BufferedReader br = new BufferedReader(response.body().charStream());
                String temp = br.readLine();
                while (temp != null && !temp.isEmpty()) {
                    list.add(temp);
                    temp = br.readLine();
                }
                success = true;
            } catch (Exception e) {
                for (int i = 0; i < e.getStackTrace().length; i++) {
                    Log.e("my", e.getStackTrace()[i].toString());
                }
            }
            if (success) {
                try {
                    CityDBHelper.this.getWritableDatabase().execSQL("DELETE from " + TABLE_LIST_CITY_NAME + " WHERE country = '" + countryName + "'");
                } catch (SQLException e) {
                    Log.e("my", "sql exception. db doesn't exist");
                }
                try {

                    Log.e("my", "success");
                    SQLiteDatabase db = CityDBHelper.this.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    City city;
                    db.beginTransaction();
                    for (int i = 0; i < list.size(); i++) {
                        city = new City(list.get(i));
                        contentValues.put("_id", city.id);
                        contentValues.put("name", city.name);
                        contentValues.put("country", city.country);
                        contentValues.put("lon", city.lon);
                        contentValues.put("lat", city.lat);
                        db.insert(TABLE_LIST_CITY_NAME, null, contentValues);
                        publishProgress(i * 100 / list.size());
                    }
                    db.setTransactionSuccessful();
                    db.endTransaction();
                //    Toast.makeText(context, "download completed", Toast.LENGTH_LONG).show();
                    Log.e("my", "add " + list.size());
                } catch (Exception e) {
                    Log.e("my", "citydbhelper: cannot be cast to updatable or sql exeption"); // не критично
                    for (int i = 0; i < e.getStackTrace().length; i++) {
                        Log.e("my", e.getStackTrace()[i].toString());
                    }
                }

            } else

            {
                Toast.makeText(context, context.getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ((ProgressBar) ((Activity) context).findViewById(R.id.progressBar)).setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            /*((Activity) context).findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
            Intent intent = new Intent(context, PrefCitiesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            */
            ((Activity) context).finish();
            ((CountriesActivity) context).overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off);
        }
    }
}