package com.faa1192.weatherforecast.Cities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.faa1192.weatherforecast.R;

import java.util.ArrayList;
import java.util.List;

//хелпер для работы с базой всех городов
public class CityDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "city_db";
    private static final int DB_VERSION = 1;
    private final Context context;

    private CityDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static CityDBHelper init(Context context) {
        return new CityDBHelper(context);
    }

    private Cursor getCursor(String query) {
        return this.getWritableDatabase().query("CITY", new String[]{"_id", "NAME"}, "Name like '%" + query + "%'", null, null, null, "Name");
    }

    public List<City> getCityList(String query) {
        Cursor cursor = getCursor(query);
        List<City> cityList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            cityList.add(new City(id, name));
        }
        return cityList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CITY (_id TEXT PRIMARY KEY, NAME TEXT);");
        try {
            String[] ids = context.getResources().getStringArray(R.array.city_id_array);
            String[] names = context.getResources().getStringArray(R.array.city_name_array);
            for (int i = 0; i < ids.length; i++) {
                ContentValues cv = new ContentValues();
                cv.put("_id", ids[i]);
                cv.put("NAME", names[i]);
                db.insert("CITY", null, cv);
            }
        } catch (SQLException e) {
            Log.e("my", "Error while creating table city");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE CITY");
        onCreate(db);
    }
}