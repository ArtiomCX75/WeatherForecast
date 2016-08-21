package com.faa1192.weatherforecast.Weather;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.faa1192.weatherforecast.Cities.City;
import com.faa1192.weatherforecast.Preferred.PrefCityDBHelper;
import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.Updatable;

//Активити содержащее сведения о погоде в конкретном городе
public class WeatherInfoActivity extends AppCompatActivity implements Updatable, SwipeRefreshLayout.OnRefreshListener {
    private City city;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(WeatherInfoActivity.this, R.string.pull_for_refresh, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_weather_info);
        Intent intent = getIntent();
        city = City.fromBundle(intent.getExtras());
        final WeatherInfoFragment fragment = (WeatherInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        fragment.setInfo(city);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange_dark)));
        actionBar.setTitle(getResources().getString(R.string.weather_in_city) + city.name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresher_weather_data);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.argb(255, 255, 0, 0), Color.argb(255, 255, 100, 0), Color.argb(255, 255, 0, 100), Color.argb(255, 255, 100, 100));
    }

    //Обновление данных о погоде с инета
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        PrefCityDBHelper.init(this).updateDataFromWeb(city);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);

    }

    //Обновление данных о погоде с базы
    @Override
    public void update() {
        final WeatherInfoFragment fragment = (WeatherInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        fragment.setInfo(PrefCityDBHelper.init(this).getCity(city.id));
    }

}