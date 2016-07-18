package com.faa1192.weatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class WeatherInfoContainer extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info_container);
        Intent intent = getIntent();
        City city = City.fromBundle(intent.getExtras());
        WeatherInfoFragment f = (WeatherInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        f.setInfo(city);
    }
}
