package com.faa1192.weatherforecast.Preferred;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.faa1192.weatherforecast.Cities.City;
import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.Updatable;
import com.faa1192.weatherforecast.Weather.WeatherData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//хелпер для работы с базой городов добавленных в избранное
public class PrefCityDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "pref_city_db";
    private static final int DB_VERSION = 1;
    private static Context context;
    private static PrefCityDBHelper dbHelper;

    private PrefCityDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        PrefCityDBHelper.context = context;
        dbHelper = this;
    }

    //Инициализация хелпера
    public static PrefCityDBHelper init(Context context) {
        return new PrefCityDBHelper(context);
    }

    private Cursor getCursor() {
        return this.getWritableDatabase().query("PREFCITY", new String[]{"_id", "NAME", "DATA"}, null, null, null, null, "Name");
    }

    //Получение города из избранного по ид
    public City getCity(int id) {
        Cursor cursor = this.getWritableDatabase().query("PREFCITY", new String[]{"_id", "NAME", "DATA"}, "_id=" + id, null, null, null, null);
        cursor.moveToFirst();
        return new City(Integer.valueOf(cursor.getString(0)), cursor.getString(1), new WeatherData(cursor.getString(2)));
    }

    //Получение списка городов из избранного
    public List<City> getCityList() {
        Cursor cursor = getCursor();
        List<City> cityList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            WeatherData weatherData = new WeatherData(cursor.getString(2));
            cityList.add(new City(id, name, weatherData));
        }
        return cityList;
    }

    //Обновление данных о погоде в городах из избранного
    public void updateAllDataFromWeb() {
        Cursor cursor = new PrefCityDBHelper(context).getCursor();
        while (cursor.moveToNext()) {
            City city = new City(Integer.valueOf(cursor.getString(0)), cursor.getString(1), new WeatherData(cursor.getString(2)));
            updateDataFromWeb(city);
        }
    }

    //Загрузка данных о погоде с инета, если данные неактуальны
    public void updateDataFromWeb(City city) {
        if (city.data.isActualData()) {
            //Toast.makeText(context, "actual", Toast.LENGTH_SHORT).show(); //for debug
            Log.e("my", "actual");
            return;
        } else {
            //Toast.makeText(context, "old", Toast.LENGTH_SHORT).show(); //for debug
            Log.e("my", "old");
        }
        WebUpdateHelper webUpdateHelper = new WebUpdateHelper();
        webUpdateHelper.execute(city);
    }

    //удаление города из избранного
    public void delFromDbPref(City city) {
        try {
            PrefCityDBHelper.init(context).getWritableDatabase().delete("PREFCITY", "_id=" + city.id, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //добавление города в избранное
    public void addToDbPref(City city) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", city.id);
            contentValues.put("NAME", city.name);
            contentValues.put("DATA", city.data.getJsonString());
            getWritableDatabase().insert("PREFCITY", null, contentValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //класс для работы с инетом
    private class WebUpdateHelper extends AsyncTask<City, Void, Void> {
        String resultString = "";
        InputStream inputStream;
        City city;
        boolean success;

        @Override
        protected Void doInBackground(City... city) {
            success = false;
            this.city = city[0];
            String urlString = "http://api.openweathermap.org/data/2.5/weather?id=" + city[0].id + "&appid=5fa682315be7b0b6b329bca80a9bbf08&lang=en&units=metric";
            Log.e("my", "url:" + urlString);
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(urlString).build();
                Response response = client.newCall(request).execute();
                BufferedReader br  = new BufferedReader(response.body().charStream());
                resultString  = br.readLine();
                success = true;
            } catch (Exception e) {
                for (int i = 0; i < e.getStackTrace().length; i++) {
                    Log.e("my", e.getStackTrace()[i].toString());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                if (success) {
                    WeatherData weatherData = new WeatherData(resultString);
                    city.data = new WeatherData(resultString);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("DATA", weatherData.getJsonString());
                    dbHelper.getWritableDatabase().update("PREFCITY", contentValues, "_id = " + city.id, null);
                //    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show(); //for debug
                    ((Updatable) context).update();
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                Log.e("my", "sql exception");
                for (int i = 0; i < e.getStackTrace().length; i++) {
                    Log.e("my", e.getStackTrace()[i].toString());
                }
            } catch (Exception e) {
                Log.e("my", "prefcitydbhelper: cannot be cast to updatable"); // не критично
                for (int i = 0; i < e.getStackTrace().length; i++) {
                    Log.e("my", e.getStackTrace()[i].toString());
                }
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PREFCITY (_id TEXT PRIMARY KEY, NAME TEXT, DATA TEXT);");
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", 551487);
            contentValues.put("NAME", "Kazan");
            db.insert("PREFCITY", null, contentValues);
            contentValues = new ContentValues();
            contentValues.put("_id", 524901);
            contentValues.put("NAME", "Moscow");
            db.insert("PREFCITY", null, contentValues);

            contentValues = new ContentValues();
            contentValues.put("_id", 582432);
            contentValues.put("NAME", "Almetyevsk");
            db.insert("PREFCITY", null, contentValues);

            contentValues = new ContentValues();
            contentValues.put("_id", 570990);
            contentValues.put("NAME", "Bolgar");
            db.insert("PREFCITY", null, contentValues);

            contentValues = new ContentValues();
            contentValues.put("_id", 521118);
            contentValues.put("NAME", "Nizhnekamsk");
            db.insert("PREFCITY", null, contentValues);
        } catch (SQLException e) {
            Log.e("my", "Error while creating table prefcity");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE PREFCITY");
        onCreate(db);
    }
}