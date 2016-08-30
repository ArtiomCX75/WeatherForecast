package com.faa1192.weatherforecast.Weather;

import android.content.Context;
import android.util.Log;

import com.faa1192.weatherforecast.Preferred.PrefCitiesActivity;
import com.faa1192.weatherforecast.R;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Date;
import java.util.HashMap;

//класс объекта "Погодные данные". Служит для десериализации json в java object
public class WeatherData {
    private String jsonString = "";
    private String cityName = ""; //название города ПРИХОДЯЩЕЕ С СЕРВЕРА
    private String humidity = ""; //влажность
    private String pressure = ""; //давление
    private String temp = ""; //температура
    private String sunrise = ""; //восход
    private String sunset = ""; //закат
    private String windSpeed = ""; //скорость ветра
    private String windDeg = ""; //направление ветра
    private String weatherDescription = ""; //описание погоды
    private String weatherMain = ""; //краткое описание погоды
    private Long time = 0L; //актуальное время сведений о погоде (приходит с сервера)
    private final String noData = "no_data";
    private static HashMap<String, String> hm = new HashMap<>();

    static {
        Context context = PrefCitiesActivity.context;
        String[] key = context.getResources().getStringArray(R.array.wd_k);
        String[] value = context.getResources().getStringArray(R.array.wd_v);
        for (int i = 0; i < key.length; i++) {
            hm.put(key[i], value[i]);
        }
    }

    public WeatherData() {
    }

    public WeatherData(String jsonString) {

        this.jsonString = jsonString;
        try {
            JSONTokener jsonTokener = new JSONTokener(jsonString);
            JSONObject baseJsonObject = new JSONObject(jsonTokener);
            try {
                try {
                    cityName = baseJsonObject.getString("name");
                } catch (Exception e) {
                }
                JSONObject mainJsonObject = new JSONObject();
                try {
                    mainJsonObject = baseJsonObject.getJSONObject("main");
                } catch (Exception e) {
                }
                try {
                    humidity = mainJsonObject.getString("humidity");
                } catch (Exception e) {
                }
                try {
                    pressure = mainJsonObject.getString("pressure");
                } catch (Exception e) {
                }
                try {
                    temp = mainJsonObject.getString("temp");
                } catch (Exception e) {
                }
                JSONObject sysJsonObject = new JSONObject();
                try {
                    sysJsonObject = baseJsonObject.getJSONObject("sys");
                } catch (Exception e) {
                }
                try {
                    sunrise = sysJsonObject.getString("sunrise");
                } catch (Exception e) {
                }
                try {
                    sunset = sysJsonObject.getString("sunset");
                } catch (Exception e) {
                }
                JSONObject windJsonObject = new JSONObject();
                try {
                    windJsonObject = baseJsonObject.getJSONObject("wind");
                } catch (Exception e) {
                }
                try {
                    windSpeed = windJsonObject.getString("speed");
                } catch (Exception e) {
                }
                try {
                    windDeg = windJsonObject.getString("deg");
                } catch (Exception e) {
                }
                JSONObject weatherJsonObject = new JSONObject();
                try {
                    weatherJsonObject = baseJsonObject.getJSONArray("weather").getJSONObject(0);
                } catch (Exception e) {
                }
                try {
                    weatherDescription = weatherJsonObject.getString("description");
                } catch (Exception e) {
                }
                try {
                    weatherMain = weatherJsonObject.getString("main");
                } catch (Exception e) {
                }
                try {
                    time = baseJsonObject.getLong("dt");
                } catch (Exception e) {
                }
            } catch (Exception e) {
                Log.e("my", "Данные о погоде некорректные или получены частично");
                throw e;
            }
        } catch (Exception e) {
            for (int i = 0; i < e.getStackTrace().length; i++) {
                Log.e("my", e.getStackTrace()[i].toString());
            }

        }
    }

    @Override //for debug
    public String toString() {
        return "cityName " + cityName + "  humidity " + humidity
                + "  pressure " + pressure + "  temp " + temp
                + "  sunrise " + sunrise + "  sunset " + sunset
                + "  windSpeed " + windSpeed + "  windDeg "
                + windDeg + "  weatherDescription " + weatherDescription
                + "  weatherMain " + weatherMain;
    }

    public String getCityName() {
        return cityName.isEmpty() ? hm.get(noData) : cityName;
    }

    public String getHumidity() {
        return humidity.isEmpty() ? hm.get(noData) : humidity + "%";
    }

    public String getJsonString() {
        return jsonString.isEmpty() ? "" : jsonString;
    }

    public String getPressure() {
        return pressure.isEmpty() ? hm.get(noData) : (((Double) (Double.valueOf(pressure) * 0.7500637554192)).toString()).substring(0, 6) + "mm";
    }

    public String getSunrise() {
        return stringToTime(sunrise);
    }

    public String getSunset() {
        return stringToTime(sunset);
    }

    public String getTemp() {
        return temp.isEmpty() ? hm.get(noData) : temp + "°C";
    }

    public String getTime() {
        return stringToTime(time.toString()) + "";
    }

    public String getWeatherDescription() {
        if (weatherDescription.isEmpty())
            weatherDescription = noData;
        weatherDescription = weatherDescription.replace(" ", "_").toLowerCase();
        return ((hm.get(weatherDescription) == null) || hm.get(weatherDescription).isEmpty()) ? weatherDescription : hm.get(weatherDescription);
    }

    public String getWeatherMain() {
        if (weatherMain.isEmpty())
            weatherMain = noData;
        weatherMain = weatherMain.replace(" ", "_").toLowerCase();
        return ((hm.get(weatherMain) == null) || hm.get(weatherMain).isEmpty()) ? weatherMain : hm.get(weatherMain);
    }

    //Направление ветра
    public String getWindDeg() {
        if (windDeg.isEmpty())
            return hm.get(noData);
        else {
            String response = "";
            double angle = Double.valueOf(windDeg);
            angle += 22.5;
            int side = (int) angle / 45;
            switch (side) {
                case 0:
                    response = "N";
                    break;
                case 1:
                    response = "NE";
                    break;
                case 2:
                    response = "E";
                    break;
                case 3:
                    response = "SE";
                    break;
                case 4:
                    response = "S";
                    break;
                case 5:
                    response = "SW";
                    break;
                case 6:
                    response = "W";
                    break;
                case 7:
                    response = "NW";
                    break;
                default:
                    response = "N";
            }
            return response;
        }
    }

    public String getWindSpeed() {
        return windSpeed.isEmpty() ? hm.get(noData) : windSpeed;
    }

    //Данные старые, если им больше часа. Сравнение со временем приходящим с сервера, а не со временем фактического получения данных
    public boolean isActualData() {
        Long curenttime = new Date().getTime() / 1000;
        //Log.e("my", "TIME:" + (curenttime - time) + ""); //fordebug
        return ((curenttime - time) < 3600);
    }

    //Преобразование строки со временем в читабельный вид с поправкой на часовой пояс +3
    private String stringToTime(String timeString) {
        if (getTemp().equals(noData))
            return hm.get(noData);
        Integer timeInt = Integer.valueOf("0" + timeString);
        int hour = (timeInt / 60 / 60) % 24;
        int min = (timeInt / 60) % 60;
        int sec = timeInt % 60;
        int timeZone = +3;
        return (hour + timeZone) % 24 + "h " + min + "m " + sec + "s";
    }

}