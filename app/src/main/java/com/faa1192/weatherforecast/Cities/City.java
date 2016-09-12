package com.faa1192.weatherforecast.Cities;

import android.os.Bundle;

import com.faa1192.weatherforecast.Weather.WeatherData;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

// класс объекта "Город". Содержит ид города (в базе, на сервере), название, и объект с информаций о погоде в городе
public class City {
    public int id = -1;
    public String name = "";
    public String country = "";
    public String lon = "";
    public String lat = "";
    public WeatherData data = new WeatherData();

    City(int id, String name, String country, String lon, String lat) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.lon = lon;
        this.lat = lat;
    }

    public City(Integer id, String name, String country, String lon, String lat, WeatherData data) {
        this(id, name, country, lon, lat);
        this.data = data;
    }

    City(String json) {
        JSONTokener jt = new JSONTokener(json);
        JSONObject jo = null;
        try {
            jo = new JSONObject(jt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            id = jo.getInt("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            name = jo.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            country = jo.getString("country");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jo = jo.getJSONObject("coord");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            lon = jo.getString("lon");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            lat = jo.getString("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return name;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("name", name);
        bundle.putString("country", country);
        bundle.putString("lon", lon);
        bundle.putString("lat", lat);
        bundle.putString("data", data.getJsonString());
        return bundle;
    }

    public static City fromBundle(Bundle bundle) {
        return new City(bundle.getInt("id"), bundle.getString("name"), bundle.getString("country"), bundle.getString("lon"), bundle.getString("lat"), new WeatherData(bundle.getString("data")));
    }

    public String getShortName(){
        if(name.contains("(")){
            return name.substring(0, name.indexOf("("));
        }
     return name;
    }

    public String getExtraName(){
        if(getShortName().length()==name.length())
            return "";
        return name.substring(getShortName().length()+1, name.length()-1);
    }
}