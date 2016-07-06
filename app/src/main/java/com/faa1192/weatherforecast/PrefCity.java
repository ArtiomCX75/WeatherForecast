package com.faa1192.weatherforecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by faa11 on 21.06.2016.
 */

public class PrefCity extends City{
    public String data = "";
    public static ArrayList<PrefCity> prefcitiesList = new ArrayList<>();

    public PrefCity(String name, int id){
        super(name, id);
    }

    public PrefCity(String name, int id, String data){
        this(name, id);
        this.data = data;
    }

    public Bundle toBundle(){
        Bundle b = super.toBundle();
        b.putString("data", data);
        return b;
    }
    public static PrefCity fromBundle(Bundle b){
        return new PrefCity(b.getString("name"), b.getInt("id"), b.getString("data"));
    }
    public void addToDB(Context context){
            try {
                ContentValues cv = new ContentValues();
                cv.put("_id", this.id);
                cv.put("NAME", this.name);
                new PrefCityDBHelper(context).getWritableDatabase().insert("PREFCITY", null, cv);

            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
}
