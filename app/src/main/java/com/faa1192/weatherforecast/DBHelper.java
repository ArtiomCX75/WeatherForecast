package com.faa1192.weatherforecast;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by faa11 on 30.09.2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    protected static final String DB_NAME = "app_db";
    protected static final String TABLE_PREF_NAME = "PREFCITY";
    protected static final String TABLE_LIST_CITY_NAME = "CITY";
    protected static final String TABLE_SYS_NAME = "SYSTEM";
    protected static final int DB_VERSION = 3;
    protected static Context context;
    protected static DBHelper dbHelper;

    protected DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        dbHelper = this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE " + TABLE_PREF_NAME + " (_id TEXT PRIMARY KEY, NAME TEXT, COUNTRY TEXT, LON TEXT, LAT TEXT, DATA TEXT);");

        } catch (SQLException e) {
            Log.e("my", "Error while creating table " + TABLE_PREF_NAME);
        }
        try {
            db.execSQL("CREATE TABLE " + TABLE_LIST_CITY_NAME + " (_id TEXT PRIMARY KEY, NAME TEXT, COUNTRY TEXT, LON TEXT, LAT TEXT);");

        } catch (SQLException e) {
            Log.e("my", "Error while creating table " + TABLE_LIST_CITY_NAME);
        }
        try {
            db.execSQL("CREATE TABLE " + TABLE_SYS_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, KEY1 TEXT, VALUE TEXT);");
        } catch (SQLException e) {
            Log.e("my", "Error while creating table " + TABLE_SYS_NAME);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 3 && oldVersion < 3) {
            try {
                context.deleteDatabase("pref_city_db");
                context.deleteDatabase("city_db");
            } catch (Exception e) {
                Log.e("MY", "error during del old db");
            }
            onCreate(db);
            return;
        }
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        try {
            context.deleteDatabase("pref_city_db");
            context.deleteDatabase("city_db");
        } catch (Exception e) {
            Log.e("MY", "error during downgrade ( while del old db)");
        }
        onCreate(db);
    }




}
