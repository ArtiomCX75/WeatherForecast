package com.faa1192.weatherforecast.Cities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.faa1192.weatherforecast.R;

public class AddCityActivity extends AppCompatActivity {
    SearchView sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Добавление города");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CE5E00")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        sw = (SearchView) findViewById(R.id.search);
        sw.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                update();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                update();
                return false;
            }
        });
    }

    public void update() {
        CitiesListFragment fr = (CitiesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_city_list);
        RecyclerView rv = (RecyclerView) fr.getView();
        CityInListAdapter cila = new CityInListAdapter(CityDBHelper.init(AddCityActivity.this).getCityList(sw.getQuery().toString()), AddCityActivity.this);
        rv.setAdapter(cila);
    }

}