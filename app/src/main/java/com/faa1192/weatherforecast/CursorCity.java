package com.faa1192.weatherforecast;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by faa11 on 29.06.2016.
 */

public class CursorCity {
    public Cursor getCursor(Context c){
        return  new CityDBHelper(c).getWritableDatabase().query("CITY", new String[] {"_id", "NAME"}, null, null, null, null, "Name");
    }


}
