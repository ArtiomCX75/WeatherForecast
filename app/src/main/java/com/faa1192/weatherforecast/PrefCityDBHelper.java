package com.faa1192.weatherforecast;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by faa11 on 07.07.2016.
 */

public class PrefCityDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "pref_city_db";
    private static final int DB_VERSION = 1;
    private static SQLiteDatabase prefdb;
    Context context;

    public PrefCityDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PREFCITY (_id TEXT, NAME TEXT, DATA TEXT);");
        try {
                ContentValues cv = new ContentValues();
                cv.put("_id", 551487);
                cv.put("NAME", "Kazan");
                db.insert("PREFCITY", null, cv);
        }
        catch (SQLException e){
//            e.printStackTrace();
        }
        finally {
            prefdb = db;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE PREFCITY");
        onCreate(db);
    }
}
