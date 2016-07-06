package com.faa1192.weatherforecast;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by faa11 on 07.07.2016.
 */

public class PrefCursorCity {
    public Cursor getCursor(Context c){
        return  new PrefCityDBHelper(c).getWritableDatabase().query("PREFCITY", new String[] {"_id", "NAME", "DATA"}, null, null, null, null, "Name");
    }
}
