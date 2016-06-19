package com.faa1192.weatherforecast;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by faa11 on 19.06.2016.
 */

public class NetworkHelper extends AsyncTask<Void, Void, Void> {
    String res = "ololo";
    public MainActivity act;


    @Override
    protected Void doInBackground(Void... voids) {
        String strUrl = "http://api.openweathermap.org/data/2.5/weather?id=551487&appid=5fa682315be7b0b6b329bca80a9bbf08&lang=en&units=metric";
        try {
            URL u1 = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) u1.openConnection();
            InputStream is =  con.getInputStream();
            byte b[] = new byte[500];
            is.read(b);
            for(int i = 0 ; i<b.length;i++) {
                res += (char) b[i];
            }
            act.text = res;
        }
        catch (Exception e){
            Log.e("my", e.toString());
        }
        return null;
    }
}
