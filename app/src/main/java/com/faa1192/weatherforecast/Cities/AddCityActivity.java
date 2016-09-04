package com.faa1192.weatherforecast.Cities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Toast;


import com.faa1192.weatherforecast.Countries.CountriesActivity;
import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.Updatable;

//Активити содержащее список всех городов
public class AddCityActivity extends AppCompatActivity implements Updatable {
    private SearchView searchView;

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
        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddCityActivity.this, getResources().getString(R.string.wait_pls), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CountriesActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.alpha_on,R.anim.alpha_off);
            }
        };
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fabcity);
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.orange_light)));
        floatingActionButton.setOnClickListener(addListener);
        floatingActionButton.setSize(FloatingActionButton.SIZE_NORMAL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1)
            return;
        String country = data.getStringExtra("country");
        Toast.makeText(AddCityActivity.this, getResources().getString(R.string.downloading), Toast.LENGTH_SHORT).show();
        // Toast.makeText(AddCityActivity.this, "c: "+country, Toast.LENGTH_SHORT).show();
        CityDBHelper.init(AddCityActivity.this).downloadCountry(country);
        //Toast.makeText(this, getResources().getString((R.string.added_city)) + city.name, Toast.LENGTH_SHORT).show();
        AddCityActivity.this.update();
    }

    //Обновление адаптера во время поиска
    public void update() {
        CitiesListFragment citiesListFragment = (CitiesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_city_list);
        RecyclerView rv = (RecyclerView) citiesListFragment.getView();
        CityInListAdapter cityInListAdapter = new CityInListAdapter(CityDBHelper.init(AddCityActivity.this).getCityList(searchView.getQuery().toString()), AddCityActivity.this);
        rv.setAdapter(cityInListAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_on,R.anim.alpha_off);
    }
}