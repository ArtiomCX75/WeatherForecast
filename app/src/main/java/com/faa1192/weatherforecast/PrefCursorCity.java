package com.faa1192.weatherforecast;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by faa11 on 07.07.2016.
 */

public class PrefCursorCity {
    static Context tempContext;
    public static Cursor getCursor(Context c){
        return  new PrefCityDBHelper(c).getWritableDatabase().query("PREFCITY", new String[] {"_id", "NAME", "DATA"}, null, null, null, null, "Name");
    }

    public static List<City> getCityList(Context c){
        tempContext = c;
        Cursor cu = getCursor(c);
        List<City > l = new ArrayList<>();
        while(cu.moveToNext()){
            int id = cu.getInt(0);
            String name = cu.getString(1);
            WeatherData wd = new WeatherData(cu.getString(2));
            l.add(new City(id, name, wd));
        }
        return l;
    }

    /*public List<WeatherData> getDataList(Context c){
        Cursor cu = this.getCursor(c);
        List<WeatherData > l = new ArrayList<>();
        while(cu.moveToNext()){
            String s = cu.getString(2);
            l.add(new WeatherData(s));
            Log.e("MY", new WeatherData(s).toString());
        }
        return l;
    }
*/
    public static void updateData(Context c){
       // tempContext = c;
        Cursor cu = getCursor(c);
        List<WeatherData > l = new ArrayList<>();
        while(cu.moveToNext()){
            City city = new City(Integer.valueOf(cu.getString(0)), cu.getString(1));
            WeatherInfoHelper wih = new WeatherInfoHelper();
            wih.execute(city);
        }

    }


    public static class WeatherInfoHelper extends AsyncTask<City, Void, Void> {
        String res = "";
        InputStream is;
        JSONObject jo;
        City myCity;
        @Override
        protected Void doInBackground(City... city) {
            myCity = city[0];
            String strUrl = "http://api.openweathermap.org/data/2.5/weather?id="+city[0].id+"&appid=5fa682315be7b0b6b329bca80a9bbf08&lang=en&units=metric";
            jo = new JSONObject();
            Log.e("my", "url:"+strUrl);
            try {
                URL u1 = new URL(strUrl);
                HttpURLConnection con = (HttpURLConnection) u1.openConnection();
                is =  con.getInputStream();
                byte b[] = new byte[500];
                is.read(b);
                for(int i = 0 ; i<b.length;i++) {
                    res += (char) b[i];
                }
                //act.text = res;

            }
            catch (Exception e){
                for(int i=0;i<e.getStackTrace().length;i++) {
                    Log.e("my", e.getStackTrace()[i].toString());
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try{
               WeatherData wd = new WeatherData(res);
                myCity.updateData(tempContext, wd);
            }
            catch (Exception e){
                for(int i=0;i<e.getStackTrace().length;i++) {
                    Log.e("my error", e.getStackTrace()[i].toString());
                }
                e.printStackTrace();
            }

        }
    }
}
