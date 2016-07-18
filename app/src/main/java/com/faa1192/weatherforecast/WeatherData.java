package com.faa1192.weatherforecast;

import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by faa11 on 14.07.2016.
 */

public class WeatherData {
    private String jsonString = "";
    private JSONTokener jsonTokener;
    private JSONObject baseJsonObject;
    private JSONObject mainJsonObject;
    private JSONObject sysJsonObject;
    private JSONObject windJsonObject;
    private JSONObject weatherJsonObject;

    private String cityName = "";
    private String humidity = "";
    private String pressure = "";
    private String temp = "";
    private String sunrise = "";
    private String sunset = "";
    private String windSpeed = "";
    private String windDeg = "";
    private String weatherDescription = "";
    private String weatherMain = "";
    private int time = 0;
    private String nd = "no data";
    private int timeZone = +3;

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




    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getHumidity() {
        return humidity.isEmpty()?nd:humidity+"%";
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getPressure() {
        return pressure.isEmpty()?nd:(new String(((Double) (new Double(pressure)*0.7500637554192)).toString())).substring(0, 6)+"mm";
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSunrise() {
        return stringToTime(sunrise);
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return stringToTime(sunset);
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    private String stringToTime(String s){
        Integer i = new Integer(s);
        int hour=(i/60/60)%24;
        int min = (i/60)%60;
        int sec = i%60;
        return (hour+timeZone)+"h "+min+"m "+sec +"s";
    }

    public JSONObject getSysJsonObject() {
        return sysJsonObject;
    }

    public void setSysJsonObject(JSONObject sysJsonObject) {
        this.sysJsonObject = sysJsonObject;
    }

    public String getTemp() {
        return temp.isEmpty()?nd:temp+"°C";
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTime() {
        return time+"";
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public JSONObject getWeatherJsonObject() {
        return weatherJsonObject;
    }

    public void setWeatherJsonObject(JSONObject weatherJsonObject) {
        this.weatherJsonObject = weatherJsonObject;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg;
    }

    public JSONObject getWindJsonObject() {
        return windJsonObject;
    }

    public void setWindJsonObject(JSONObject windJsonObject) {
        this.windJsonObject = windJsonObject;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }
}
