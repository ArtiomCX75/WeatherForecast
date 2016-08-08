package com.faa1192.weatherforecast.Cities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.faa1192.weatherforecast.R;

public class AddCityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Добавление города");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}