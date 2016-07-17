package com.faa1192.weatherforecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by faa11 on 21.06.2016.
 */

public class City {
    public int id = -1;
    public String name = "";
  //  public String data ="";
    public WeatherData data = new WeatherData();
    static ArrayList<City> citiesList = new ArrayList<>();

    public City(){
    }

    City(int id, String name){
        this.id = id;
        this.name = name;
    }

    City(int id, String name, WeatherData data){
        this(id, name);
        this.data = data;
    }

    @Override
    public String toString() {
        return name;
    }

    public Bundle toBundle(){
        Bundle b = new Bundle();
        b.putInt("id", id);
        b.putString("name", name);
        b.putString("data", data.jsonString);
        return b;
    }

    static City fromBundle(Bundle b){
        return new City(b.getInt("id"), b.getString("name"), new WeatherData(b.getString("data")));
    }

    void addToDbPref(Context context){
        try {
            ContentValues cv = new ContentValues();
            cv.put("_id", this.id);
            cv.put("NAME", this.name);
            cv.put("DATA", this.data.jsonString);
            new PrefCityDBHelper(context).getWritableDatabase().insert("PREFCITY", null, cv);

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    void updateData(Context context, WeatherData wd){
        try {
            ContentValues cv = new ContentValues();
//            cv.put("_id", this.id);
//            cv.put("NAME", this.name);
            cv.put("DATA", wd.jsonString);
            new PrefCityDBHelper(context).getWritableDatabase().update("PREFCITY", cv, "_id = "+ id, null);
//                    insert("PREFCITY", null, cv);

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void delFromDbPref(Context context){
        try {
            new PrefCityDBHelper(context).getWritableDatabase().delete("PREFCITY", "_id="+id, null);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
