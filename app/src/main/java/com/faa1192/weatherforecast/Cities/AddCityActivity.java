package com.faa1192.weatherforecast.Cities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
//import android.widget.SearchView;

import com.faa1192.weatherforecast.R;

//Активити содержащее список всех городов
public class AddCityActivity extends AppCompatActivity {
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.adding_city));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange_dark)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        searchView.setFocusable(false);


    }

    //Обновление адаптера во время поиска
    public void update() {
        CitiesListFragment citiesListFragment = (CitiesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_city_list);
        RecyclerView rv = (RecyclerView) citiesListFragment.getView();
        CityInListAdapter cityInListAdapter = new CityInListAdapter(CityDBHelper.init(AddCityActivity.this).getCityList(searchView.getQuery().toString()), AddCityActivity.this);
        rv.setAdapter(cityInListAdapter);
    }
}