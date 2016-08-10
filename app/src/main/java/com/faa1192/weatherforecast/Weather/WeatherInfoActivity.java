package com.faa1192.weatherforecast.Weather;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.faa1192.weatherforecast.Cities.City;
import com.faa1192.weatherforecast.Preferred.PrefCityDBHelper;
import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.Updatable;

public class WeatherInfoActivity extends AppCompatActivity implements Updatable, SwipeRefreshLayout.OnRefreshListener {
    private City city;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(WeatherInfoActivity.this, "Swipe to update", Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_weather_info);
        Intent intent = getIntent();
        city = City.fromBundle(intent.getExtras());
        final WeatherInfoFragment f = (WeatherInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        f.setInfo(city);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CE5E00")));
        actionBar.setTitle("Погода в городе: " + city.name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresher_weather_data);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        PrefCityDBHelper.init(this).updateDataFromWeb(city);
        Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
        // update();
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void update() {
        final WeatherInfoFragment f = (WeatherInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        f.setInfo(PrefCityDBHelper.init(this).getCity(city.id));
        Toast.makeText(this, "update", Toast.LENGTH_SHORT).show();
    }
}