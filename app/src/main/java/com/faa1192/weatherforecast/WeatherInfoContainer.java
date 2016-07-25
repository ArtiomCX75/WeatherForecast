package com.faa1192.weatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

public class WeatherInfoContainer extends FragmentActivity {
City city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info_container);
        Intent intent = getIntent();
        city = City.fromBundle(intent.getExtras());
        final WeatherInfoFragment f = (WeatherInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        f.setInfo(city);
        Button refExData = (Button) findViewById(R.id.refresh_extra_data);
        refExData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefCursorCity.updateData(getApplicationContext(), city);
                f.setInfo(PrefCursorCity.getCity(getApplicationContext(), city.id));
            }
        });
    }
}
