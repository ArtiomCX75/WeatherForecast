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
    Context tempContext;
    public Cursor getCursor(Context c){
        return  new PrefCityDBHelper(c).getWritableDatabase().query("PREFCITY", new String[] {"_id", "NAME", "DATA"}, null, null, null, null, "Name");
    }

    public List<String> getCityList(Context c){
        Cursor cu = this.getCursor(c);
        List<String > l = new ArrayList<>();
        while(cu.moveToNext()){
            String s = cu.getString(1);
            l.add(s);
        }
        return l;
    }

    public List<WeatherData> getDataList(Context c){
        Cursor cu = this.getCursor(c);
        List<WeatherData > l = new ArrayList<>();
        while(cu.moveToNext()){
            String s = cu.getString(2);
            l.add(new WeatherData(s));
            Log.e("MY", new WeatherData(s).toString());
        }
        return l;
    }

    public void updateData(Context c){
        tempContext = c;
        Cursor cu = this.getCursor(c);
        List<WeatherData > l = new ArrayList<>();
        while(cu.moveToNext()){
            City city = new City(Integer.valueOf(cu.getString(0)), cu.getString(1));
            WeatherInfoHelper2 wih = new WeatherInfoHelper2();
            wih.execute(city);
        }

    }


    public class WeatherInfoHelper2 extends AsyncTask<City, Void, Void> {
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
