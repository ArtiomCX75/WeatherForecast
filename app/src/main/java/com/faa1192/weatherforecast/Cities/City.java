package com.faa1192.weatherforecast.Cities;

import android.os.Bundle;

import com.faa1192.weatherforecast.Weather.WeatherData;

// класс объекта "Город". Содержит ид города (в базе, на сервере), название, и объект с информаций о погоде в городе
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
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("name", name);
        bundle.putString("data", data.getJsonString());
        return bundle;
    }

    public static City fromBundle(Bundle bundle) {
        return new City(bundle.getInt("id"), bundle.getString("name"), new WeatherData(bundle.getString("data")));
    }
}