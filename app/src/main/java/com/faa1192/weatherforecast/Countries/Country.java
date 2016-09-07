package com.faa1192.weatherforecast.Countries;

import android.content.Context;

import com.faa1192.weatherforecast.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by faa11 on 07.09.2016.
 */
public class Country {
    public Map<String, Integer> map = new HashMap<>();
    Context context;

    public Country(Context context) {
        this.context = context;
        map.put("RU", R.string.RU);
        map.put("UA", R.string.UA);
    }

    public String getName(String s) {
        Integer i = map.get(s);
        if (i == null)
            return s;
        return context.getResources().getString(i);
    }

}