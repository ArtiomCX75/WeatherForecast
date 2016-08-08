package com.faa1192.weatherforecast.Preferred;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.faa1192.weatherforecast.Cities.AddCityActivity;
import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.Updatable;

public class PrefCitiesActivity extends AppCompatActivity implements Updatable, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(PrefCitiesActivity.this, "pull for refresh", Toast.LENGTH_LONG).show();
        Toast.makeText(PrefCitiesActivity.this, "hold for delete", Toast.LENGTH_LONG).show();
        PrefCityDBHelper.init(getApplicationContext()).updateAllDataFromWeb();
        setContentView(R.layout.activity_pref_list);
        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PrefCitiesActivity.this, "Подождите...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AddCityActivity.class);
                startActivityForResult(intent, 1);
            }
        };
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Избранное");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(addListener);
        fab.setSize(FloatingActionButton.SIZE_NORMAL);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PrefCitiesActivity.this.update();
    }

    @Override
    public void update() {
        PrefCitiesListFragment fr = (PrefCitiesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_pref);
        RecyclerView rv = (RecyclerView) fr.getView();
        CityWithTempAdapter cwta = new CityWithTempAdapter(PrefCityDBHelper.init(PrefCitiesActivity.this).getCityList(), PrefCitiesActivity.this);
        rv.setAdapter(cwta);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        PrefCityDBHelper.init(PrefCitiesActivity.this).updateAllDataFromWeb();
        PrefCitiesActivity.this.update();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}