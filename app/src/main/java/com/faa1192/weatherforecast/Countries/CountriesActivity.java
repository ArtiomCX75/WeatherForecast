package com.faa1192.weatherforecast.Countries;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.faa1192.weatherforecast.R;
//Активити содержащее список всех стран
public class CountriesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.adding_cities));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange_dark)));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_on,R.anim.alpha_off);
    }
}
