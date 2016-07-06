package com.faa1192.weatherforecast;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by faa11 on 21.06.2016.
 */

public class City {
    public String name = "";
    public int id = -1;
    public static ArrayList<City> citiesList = new ArrayList<>();

    public City(){

    }

    public City(String name, int id){
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public Bundle toBundle(){
        Bundle b = new Bundle();
        b.putString("name", name);
        b.putInt("id", id);
        return b;
    }
    public static City fromBundle(Bundle b){
        return new City(b.getString("name"), b.getInt("id"));
    }
}
