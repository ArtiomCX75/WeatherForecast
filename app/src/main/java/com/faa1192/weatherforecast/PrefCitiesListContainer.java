package com.faa1192.weatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PrefCitiesListContainer extends AppCompatActivity implements Updatable, SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(PrefCitiesListContainer.this, "Swipe to update", Toast.LENGTH_LONG).show();
        PrefCityDBHelper.init(getApplicationContext()).updateAllDataFromWeb();
        setContentView(R.layout.pref_list_container);
        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PrefCitiesListContainer.this, "Подождите...", Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(getApplicationContext(), AddCityActivity.class);
                startActivityForResult(intent, 1);
            }
        };
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Избранное");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(addListener);
        fab.setSize(FloatingActionButton.SIZE_NORMAL);
        mSwipeRefreshLayout  = (SwipeRefreshLayout) findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PrefCitiesListContainer.this.update();
    }

    @Override
    public void update() {
        PrefCitiesListFragment fr = (PrefCitiesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_pref);
        RecyclerView rv = (RecyclerView) fr.getView();
        CityWithTempAdapter cwta = new CityWithTempAdapter(PrefCityDBHelper.init(PrefCitiesListContainer.this).getCityList(), PrefCitiesListContainer.this);
        rv.setAdapter(cwta);
        Toast.makeText(PrefCitiesListContainer.this, "update", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        PrefCityDBHelper.init(PrefCitiesListContainer.this).updateAllDataFromWeb();
        PrefCitiesListContainer.this.update();
        mSwipeRefreshLayout.setRefreshing(false);
        }
}