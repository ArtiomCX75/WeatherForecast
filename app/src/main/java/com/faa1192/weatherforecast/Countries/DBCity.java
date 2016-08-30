package com.faa1192.weatherforecast.Countries;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by faa11 on 30.08.2016.
 */
public class DBCity {
    public int id;
    public String name;
    public String country;
    public String lon;
    public String lat;

    public DBCity(){

    }

    public DBCity(String s){
        JSONTokener jt = new JSONTokener(s);
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
            country = jo.getString("_id");
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

}
