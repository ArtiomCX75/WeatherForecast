package com.faa1192.weatherforecast;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherInfo extends Fragment {


    public WeatherInfo() {
        // Required empty public constructor
    }

    private  void setInfo(String s){

        ((TextView) getActivity().findViewById(R.id.info)).setText(s);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_info, container, false);
    }

    public void showWeather(City city){
        WeatherInfoHelper wih = new WeatherInfoHelper();
        wih.execute(city);
    }


   public class WeatherInfoHelper extends AsyncTask<City, Void, Void> {
        String res = "";
       InputStream is;
       JSONObject jo;
        @Override
        protected Void doInBackground(City... city) {
            String strUrl = "http://api.openweathermap.org/data/2.5/weather?id="+city[0].id+"&appid=5fa682315be7b0b6b329bca80a9bbf08&lang=en&units=metric";
            jo = new JSONObject();
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(is)); //new InputStreamReader(con.getContent(), "UTF-8"));
            String json = reader.readLine();
            JSONTokener token = new JSONTokener(res);
            //     JSONArray finalResult = new JSONArray(token);
            jo = new JSONObject(token);
                res = jo.getString("name");
                res+="  ";
            JSONObject main = jo.getJSONObject("main");
                res+=main.getString("temp")+"C";
                res+="  ";
            Double q = main.getDouble("pressure");
            q=0.75006*q*100;
            q = (double) Math.round(q);
            q/=100;
                res+=q+"mm";
                res+=" ";
                res+=main.getString("humidity")+"%";
            setInfo(res);
            }
            catch (Exception e){
                for(int i=0;i<e.getStackTrace().length;i++) {
                    Log.e("my", e.getStackTrace()[i].toString());
                }
            }

        }
    }



}
