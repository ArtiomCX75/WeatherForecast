package com.faa1192.weatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WeatherInfoContainer extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    City city;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(WeatherInfoContainer.this, "Swipe to update", Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_weather_info_container);
        Intent intent = getIntent();
        city = City.fromBundle(intent.getExtras());
        final WeatherInfoFragment f = (WeatherInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        f.setInfo(city);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Погода в городе: "+city.name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mSwipeRefreshLayout  = (SwipeRefreshLayout) findViewById(R.id.refresher_weather_data);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        PrefCityDBHelper.init(getApplicationContext()).updateDataFromWeb(city);
        final WeatherInfoFragment f = (WeatherInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        f.setInfo(PrefCityDBHelper.init(getApplicationContext()).getCity(city.id));
        mSwipeRefreshLayout.setRefreshing(false);
    }
}