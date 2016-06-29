package com.faa1192.weatherforecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by faa11 on 28.06.2016.
 */

public class CityDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "city_db";
    private static final int DB_VERSION = 1;
    Context context;

    public CityDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CITY (_id TEXT, NAME TEXT);");
        try {
            String[] ids = context.getResources().getStringArray(R.array.city_id_array);
            String[] names = context.getResources().getStringArray(R.array.city_name_array);
            for (int i = 0; i < ids.length; i++) {
                ContentValues cv = new ContentValues();
                cv.put("_id", ids[i]);
                cv.put("NAME", names[i]);
                db.insert("CITY", null, cv);
            }
        }
        catch (SQLException e){
//            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE CITY");
        onCreate(db);
    }
}
