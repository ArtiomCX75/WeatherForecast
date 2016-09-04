package com.faa1192.weatherforecast.Cities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.Updatable;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//хелпер для работы с базой всех городов
public class CityDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "city_db";
    private static final String TABLE_NAME = "CITY";

    private static final int DB_VERSION = 2;
    private final Context context;

    // SQLiteDatabase db;
    private CityDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static CityDBHelper init(Context context) {
        return new CityDBHelper(context);
    }

    private Cursor getCursor(String query) {
        return this.getWritableDatabase().query(TABLE_NAME, new String[]{"_id", "NAME", "country", "lon", "lat"}, "Name like '%" + query + "%'", null, null, null, "Name");
    }

    public List<City> getCityList(String query) {
        Cursor cursor = getCursor(query);
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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id TEXT PRIMARY KEY, NAME TEXT, COUNTRY TEXT, LON TEXT, LAT TEXT);");
        /*try {
//            String[] ids = context.getResources().getStringArray(R.array.city_id_array);
//            String[] names = context.getResources().getStringArray(R.array.city_name_array);
            for (int i = 0; i < ids.length; i++) {
                ContentValues cv = new ContentValues();
                cv.put("_id", ids[i]);
                cv.put("NAME", names[i]);
                db.insert("CITY", null, cv);

            }
            //  this.db = db;
            //CitiesOfCountry coc = new CitiesOfCountry();
            //coc.execute("UA");
        } catch (SQLException e) {
            Log.e("my", "Error while creating table " + TABLE_NAME);
        }*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try {
            db.execSQL("DROP TABLE " + TABLE_NAME);
        } catch (SQLException e) {
            Log.e("my", "sql exception. db doesn't exist");
        }
        onCreate(db);
    }

    public void downloadCountry(String str) {
        CitiesOfCountry coc = new CitiesOfCountry();
        coc.execute(str);
    }

    //Загрузка городов с инета
    private class CitiesOfCountry extends AsyncTask<String, Void, Void> {
        // String resultString = "";
        //  InputStream inputStream;
        //   byte data[];
        String countryName = "";
        ArrayList<String> list = new ArrayList<>();

        // City city;
        boolean success;

        @Override
        protected Void doInBackground(String... strings) {
            success = false;
            countryName = strings[0];
            String urlString = "https://raw.githubusercontent.com/ArtiomCX75/WeatherForecast/move_citieslist_to_server/citieslist/" + countryName + ".txt";
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
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (success) {
                try {
                    CityDBHelper.this.getWritableDatabase().execSQL("DELETE " + TABLE_NAME + " WHERE country = '" + countryName + "'");
                } catch (SQLException e) {
                    Log.e("my", "sql exception. db doesn't exist");
                }
                try {
                    Log.e("my", "success");
                    for (int i = 0; i < list.size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        City city = new City(list.get(i));
                        contentValues.put("_id", city.id);
                        contentValues.put("name", city.name);
                        contentValues.put("country", city.country);
                        contentValues.put("lon", city.lon);
                        contentValues.put("lat", city.lat);
                        CityDBHelper.this.getWritableDatabase().insert(TABLE_NAME, null, contentValues);
                    }
                    Log.e("my", "add " + list.size());
                    ((Updatable) context).update();
                } catch (SQLException e) {
                    Log.e("my", "sql exception");
                    for (int i = 0; i < e.getStackTrace().length && i < 2; i++) {
                        Log.e("my", e.getStackTrace()[i].toString());
                    }
                } catch (Exception e) {
                    Log.e("my", "prefcitydbhelper: cannot be cast to updatable"); // не критично
                    for (int i = 0; i < e.getStackTrace().length; i++) {
                        //Log.e("my", e.getStackTrace()[i].toString());
                    }
                }
            } else {
                Toast.makeText(context, context.getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
            }
        }
    }
}