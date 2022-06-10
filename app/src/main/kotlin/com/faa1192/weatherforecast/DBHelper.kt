package com.faa1192.weatherforecast

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by faa11 on 30.09.2016.
 */

const val DB_NAME = "app_db"
const val TABLE_PREF_NAME = "PREFCITY"
const val TABLE_LIST_CITY_NAME = "CITY"
const val TABLE_SYS_NAME = "SYSTEM"
const val DB_VERSION = 3

open class DBHelper protected constructor(var context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    val dbHelper = this


    override fun onCreate(db: SQLiteDatabase) {
        try {
            db.execSQL("CREATE TABLE $TABLE_PREF_NAME (_id TEXT PRIMARY KEY, NAME TEXT, COUNTRY TEXT, LON TEXT, LAT TEXT, DATA TEXT);")
        } catch (e: SQLException) {
            Log.e("my", "Error while creating table $TABLE_PREF_NAME")
        }
        try {
            db.execSQL("CREATE TABLE $TABLE_LIST_CITY_NAME (_id TEXT PRIMARY KEY, NAME TEXT, COUNTRY TEXT, LON TEXT, LAT TEXT);")
        } catch (e: SQLException) {
            Log.e("my", "Error while creating table $TABLE_LIST_CITY_NAME")
        }
        try {
            db.execSQL("CREATE TABLE $TABLE_SYS_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, KEY1 TEXT, VALUE TEXT);")
        } catch (e: SQLException) {
            Log.e("my", "Error while creating table $TABLE_SYS_NAME")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion == 3 && oldVersion < 3) {
            try {
                context.deleteDatabase("pref_city_db")
                context.deleteDatabase("city_db")
            } catch (e: Exception) {
                Log.e("MY", "error during del old db")
            }
            onCreate(db)
            return
        }
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        super.onDowngrade(db, oldVersion, newVersion)
        try {
            context.deleteDatabase("pref_city_db")
            context.deleteDatabase("city_db")
        } catch (e: Exception) {
            Log.e("MY", "error during downgrade ( while del old db)")
        }
        onCreate(db)
    }


}