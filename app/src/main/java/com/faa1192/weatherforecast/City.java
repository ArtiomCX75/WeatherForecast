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
        b.putString("data", data.getJsonString());
        return b;
    }

    static City fromBundle(Bundle b){
        return new City(b.getInt("id"), b.getString("name"), new WeatherData(b.getString("data")));
    }
}