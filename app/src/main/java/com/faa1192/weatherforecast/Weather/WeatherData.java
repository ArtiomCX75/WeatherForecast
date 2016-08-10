package com.faa1192.weatherforecast.Weather;

import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Date;

public class WeatherData {
    private String jsonString = "";

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
    private Long time = 0L;
    private final String nd = "no data";

    public WeatherData() {
    }

    public WeatherData(String jsonString) {
        int i = 0;
        try {
            i = 1;
            this.jsonString = jsonString;
            i = 2;
            JSONTokener jsonTokener = new JSONTokener(jsonString);
            i = 3;
            JSONObject baseJsonObject = new JSONObject(jsonTokener);
            i = 4;
            cityName = baseJsonObject.getString("name");
            i = 5;
            JSONObject mainJsonObject = baseJsonObject.getJSONObject("main");
            i++;
            humidity = mainJsonObject.getString("humidity");
            i++;
            pressure = mainJsonObject.getString("pressure");
            i++;
            temp = mainJsonObject.getString("temp");
            i++;
            JSONObject sysJsonObject = baseJsonObject.getJSONObject("sys");
            i = 10;
            sunrise = sysJsonObject.getString("sunrise");
            i = 11;
            sunset = sysJsonObject.getString("sunset");
            i = 12;
            JSONObject windJsonObject = baseJsonObject.getJSONObject("wind");
            i = 13;
            windSpeed = windJsonObject.getString("speed");
            i = 14;
            windDeg = windJsonObject.getString("deg");
            i = 15;
            JSONObject weatherJsonObject = baseJsonObject.getJSONArray("weather").getJSONObject(0);
            i++;
            weatherDescription = weatherJsonObject.getString("description");
            i++;
            weatherMain = weatherJsonObject.getString("main");
            i++;
            time = baseJsonObject.getLong("dt");
        } catch (Exception e) {
            Log.e("my", "WeatherData weren't received or were received partially " + i);
        }
    }

    @Override
    public String toString() {
        return "cityName " + cityName + "  humidity " + humidity
                + "  pressure " + pressure + "  temp " + temp
                + "  sunrise " + sunrise + "  sunset " + sunset
                + "  windSpeed " + windSpeed + "  windDeg "
                + windDeg + "  weatherDescription " + weatherDescription
                + "  weatherMain " + weatherMain;
    }


    public String getCityName() {
        return cityName.isEmpty() ? nd : cityName;
    }

    public String getHumidity() {
        return humidity.isEmpty() ? nd : humidity + "%";
    }

    public String getJsonString() {
        return jsonString.isEmpty() ? "" : jsonString;
    }

    public String getPressure() {
        return pressure.isEmpty() ? nd : (((Double) (Double.valueOf(pressure) * 0.7500637554192)).toString()).substring(0, 6) + "mm";
    }

    public String getSunrise() {
        return stringToTime(sunrise);
    }

    public String getSunset() {
        return stringToTime(sunset);
    }


    public String getTemp() {
        return temp.isEmpty() ? nd : temp + "°C";
    }

    public String getTime() {
        return stringToTime(time.toString()) + "";
    }

    public String getWeatherDescription() {
        return weatherDescription.isEmpty() ? nd : weatherDescription;
    }

    public String getWeatherMain() {
        return weatherMain.isEmpty() ? nd : weatherMain;
    }

    public String getWindDeg() {
        if (windDeg.isEmpty())
            return nd;
        else {
            String s = "";
            double ang = Double.valueOf(windDeg);
            if (ang < 23)
                s = "N";
            else {
                if (ang < 68)
                    s = "NE";
                else {
                    if (ang < 113)
                        s = "E";
                    else {
                        if (ang < 158)
                            s = "SE";
                        else {
                            if (ang < 203)
                                s = "S";
                            else {
                                if (ang < 248)
                                    s = "SW";
                                else {
                                    if (ang < 293)
                                        s = "W";
                                    else {
                                        if (ang < 338)
                                            s = "NW";
                                        else
                                            s = "N";
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return s;
        }
    }

    public String getWindSpeed() {
        return windSpeed.isEmpty() ? nd : windSpeed;
    }

    public boolean isActualData() {
        Long curtime = new Date().getTime() / 1000;
        Log.e("my", "TIME:" + (curtime - time) + "");
        return ((curtime - time) < 2900);
    }

    private String stringToTime(String s) {
        if (getTemp().equals(nd))
            return nd;
        Integer i = Integer.valueOf("0" + s);
        int hour = (i / 60 / 60) % 24;
        int min = (i / 60) % 60;
        int sec = i % 60;
        int timeZone = +3;
        return (hour + timeZone) % 24 + "h " + min + "m " + sec + "s";
    }
}