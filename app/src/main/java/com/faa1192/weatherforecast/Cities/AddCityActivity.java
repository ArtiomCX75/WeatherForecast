package com.faa1192.weatherforecast.Cities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.faa1192.weatherforecast.Countries.CountriesActivity;
import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.Updatable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

//Активити содержащее список всех городов
public class AddCityActivity extends AppCompatActivity implements Updatable {
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.col_pr_dark)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color=\""+getResources().getColor(R.color.pr_text)+"\">" + getString(R.string.adding_city) + "</font>"));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.pr_text), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
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
               // Toast.makeText(AddCityActivity.this, getResources().getString(R.string.wait_pls), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CountriesActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.alpha_on,R.anim.alpha_off);
            }
        };
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fabcity);
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.col_pr)));
        floatingActionButton.setOnClickListener(addListener);
        floatingActionButton.setSize(FloatingActionButton.SIZE_NORMAL);
        floatingActionButton.setColorFilter(getResources().getColor(R.color.sec_text));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1)
            return;
        AddCityActivity.this.update();
    }

    //Обновление адаптера во время поиска
    public void update() {
        CitiesListFragment citiesListFragment = (CitiesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_city_list);
        RecyclerView rv = (RecyclerView) citiesListFragment.getView();
        CityInListAdapter cityInListAdapter = new CityInListAdapter(CityDBHelper.init(AddCityActivity.this).getCityList(searchView.getQuery().toString().trim()), AddCityActivity.this);
        rv.setAdapter(cityInListAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_on,R.anim.alpha_off);
    }
}