package com.faa1192.weatherforecast;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faa11 on 07.07.2016.
 */

public class PrefCursorCity {
    public Cursor getCursor(Context c){
        return  new PrefCityDBHelper(c).getWritableDatabase().query("PREFCITY", new String[] {"_id", "NAME", "DATA"}, null, null, null, null, "Name");
    }

    public List<String> getList(Context c){
        Cursor cu = this.getCursor(c);
        List<String > l = new ArrayList<>();
        while(cu.moveToNext()){
            String s = cu.getString(1);
            l.add(s);
        }
        return l;
    }
}
