package com.faa1192.weatherforecast.Cities;

import android.os.Bundle;

import com.faa1192.weatherforecast.Weather.WeatherData;

public class City {
    public int id = -1;
    public String name = "";
    public WeatherData data = new WeatherData();

    City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public City(int id, String name, WeatherData data) {
        this(id, name);
        this.data = data;
    }

    @Override
    public String toString() {
        return name;
    }

    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putInt("id", id);
        b.putString("name", name);
        b.putString("data", data.getJsonString());
        return b;
    }

    public static City fromBundle(Bundle b) {
        return new City(b.getInt("id"), b.getString("name"), new WeatherData(b.getString("data")));
    }
}