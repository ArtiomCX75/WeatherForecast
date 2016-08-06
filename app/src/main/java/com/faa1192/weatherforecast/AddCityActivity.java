package com.faa1192.weatherforecast;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by faa11 on 07.07.2016.
 */

public class AddCityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_city_container);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Добавление города");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}