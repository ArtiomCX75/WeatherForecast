package com.faa1192.weatherforecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by faa11 on 07.07.2016.
 */

public class PrefCityDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "pref_city_db";
    private static final int DB_VERSION = 1;
  //  private static SQLiteDatabase prefdb;
    private static Context context;
    private static PrefCityDBHelper dbHelper;

    private PrefCityDBHelper(Context c) {
        super(c, DB_NAME, null, DB_VERSION);
        context = c;
        dbHelper = this;
    }

    public static PrefCityDBHelper  init(Context c){
        return new PrefCityDBHelper(c);
    }

    public Cursor getCursor(){
        return  this.getWritableDatabase().query("PREFCITY", new String[] {"_id", "NAME", "DATA"}, null, null, null, null, "Name");
    }

    public City getCity(int id){
        Cursor cursor = this.getWritableDatabase().query("PREFCITY", new String[] {"_id", "NAME", "DATA"}, "_id="+id, null, null, null, null);
        cursor.moveToFirst();
        City city = new City(Integer.valueOf(cursor.getString(0)), cursor.getString(1), new WeatherData(cursor.getString(2)));
        return city;
    }

    public List<City> getCityList(){
        Cursor cu = getCursor();
        List<City > l = new ArrayList<>();
        while(cu.moveToNext()){
            int id = cu.getInt(0);
            String name = cu.getString(1);
            WeatherData wd = new WeatherData(cu.getString(2));
            l.add(new City(id, name, wd));
        }
        return l;
    }

    public void updateAllDataFromWeb(){
        Cursor cu = new PrefCityDBHelper(context).getCursor();
        while(cu.moveToNext()){
            City city = new City(Integer.valueOf(cu.getString(0)), cu.getString(1), new WeatherData(cu.getString(2)));
            updateDataFromWeb(city);
        }

    }

    public void updateDataFromWeb(City city){
        if(city.data.isActualData()) {
            Toast.makeText(context, "actual", Toast.LENGTH_SHORT).show();
            Log.e("my", "actual");
            return;
        }
        else{
            Toast.makeText(context, "old", Toast.LENGTH_SHORT).show();
            Log.e("my", "old");
        }
        WebUpdateHelper wih = new WebUpdateHelper();
        wih.execute(city);
    }

    public void delFromDbPref(City city){
        try {
            PrefCityDBHelper.init(context).getWritableDatabase().delete("PREFCITY", "_id="+city.id, null);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addToDbPref(City city){
        try {
            ContentValues cv = new ContentValues();
            cv.put("_id", city.id);
            cv.put("NAME", city.name);
            cv.put("DATA", city.data.getJsonString());
            getWritableDatabase().insert("PREFCITY", null, cv);

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private class WebUpdateHelper extends AsyncTask<City, Void, Void> {
        String res = "";
        InputStream is;
        JSONObject jo;
        City myCity;
        boolean success = false;
        @Override
        protected Void doInBackground(City... city) {
            myCity = city[0];
            String strUrl = "http://api.openweathermap.org/data/2.5/weather?id="+city[0].id+"&appid=5fa682315be7b0b6b329bca80a9bbf08&lang=en&units=metric";
            jo = new JSONObject();
            Log.e("my", "url:"+strUrl);
            try {
                URL u1 = new URL(strUrl);
                HttpURLConnection con = (HttpURLConnection) u1.openConnection();
                is =  con.getInputStream();
                byte b[] = new byte[500];
                is.read(b);
                for(int i = 0 ; i<b.length;i++) {
                    res += (char) b[i];
                }
            }
            catch (Exception e){
                for(int i=0;i<e.getStackTrace().length;i++) {
                    Log.e("my", e.getStackTrace()[i].toString());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{
                WeatherData wd = new WeatherData(res);
                myCity.data = new WeatherData(res);//)  .updateData(context, wd);
                ContentValues cv = new ContentValues();
                cv.put("DATA", wd.getJsonString());
                dbHelper.getWritableDatabase().update("PREFCITY", cv, "_id = "+ myCity.id, null);

                }
                catch (SQLException e){
                    for(int i=0;i<e.getStackTrace().length;i++) {
                        Log.e("my error", e.getStackTrace()[i].toString());
                    }
                    e.printStackTrace();
                }
                try{
                    ((Updatable) context).update();
                }
                catch (Exception e){
                    Log.e("my", "prefcitydbhelper: cannot be cast to updatable");
                }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PREFCITY (_id TEXT, NAME TEXT, DATA TEXT);");
        try {
                ContentValues cv = new ContentValues();
                cv.put("_id", 551487);
                cv.put("NAME", "Kazan");
                db.insert("PREFCITY", null, cv);
        }
        catch (SQLException e){}
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE PREFCITY");
        onCreate(db);
    }
}