package com.faa1192.weatherforecast;

import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by faa11 on 14.07.2016.
 */

public class WeatherData {
    String jsonString = "";
    JSONTokener jsonTokener;
    JSONObject baseJsonObject;
    JSONObject mainJsonObject;
    JSONObject sysJsonObject;
    JSONObject windJsonObject;
    JSONObject weatherJsonObject;

    String cityName = "";
    String humidity = "";
    String pressure = "";
    String temp = "";
    String sunrise = "";
    String sunset = "";
    String windSpeed = "";
    String windDeg = "";
    String weatherDescription = "";
    String weatherMain = "";
    int time = 0;

    public WeatherData(){}

    public  WeatherData(String jsonString) {
        try {
            this.jsonString = jsonString;
            jsonTokener = new JSONTokener(jsonString);
            baseJsonObject = new JSONObject(jsonTokener);
            cityName = baseJsonObject.getString("name");
            mainJsonObject = baseJsonObject.getJSONObject("main");
            humidity = mainJsonObject.getString("humidity");
            pressure = mainJsonObject.getString("pressure");
            temp = mainJsonObject.getString("temp");
            sysJsonObject = baseJsonObject.getJSONObject("sys");
            sunrise = sysJsonObject.getString("sunrise");
            sunset = sysJsonObject.getString("sunset");
            windJsonObject = baseJsonObject.getJSONObject("wind");
            windSpeed = windJsonObject.getString("speed");
            windDeg = windJsonObject.getString("deg");
            weatherJsonObject = baseJsonObject.getJSONArray("weather").getJSONObject(0);
            weatherDescription = weatherJsonObject.getString("description");
            weatherMain = weatherJsonObject.getString("main");
        }
        catch (Exception e){
            for(int i=0;i<e.getStackTrace().length;i++) {
                Log.e("my error wd", e.getStackTrace()[i].toString());
            }
        }
    }

    @Override
    public String toString(){
        String s =  "cityName "+cityName + "  humidity " + humidity
                + "  pressure " + pressure + "  temp " + temp
                + "  sunrise " + sunrise + "  sunset "  + sunset
                + "  windSpeed " + windSpeed + "  windDeg "
                + windDeg + "  weatherDescription "+ weatherDescription
                + "  weatherMain " +weatherMain;
        Log.e("MY", s);
        return s;
    }


}
