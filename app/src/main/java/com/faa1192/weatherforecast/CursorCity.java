package com.faa1192.weatherforecast;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faa11 on 29.06.2016.
 */

public class CursorCity {
    public Cursor getCursor(Context c){
        return  new CityDBHelper(c).getWritableDatabase().query("CITY", new String[] {"_id", "NAME"}, null, null, null, null, "Name");
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
}
