package com.faa1192.weatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PrefCitiesListContainer extends FragmentActivity  implements Updatable{
    PrefCitiesListContainer ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ma = this;
        PrefCityDBHelper.init(getApplicationContext()).updateAllDataFromWeb();
        setContentView(R.layout.pref_list_container);
        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(getApplicationContext(), AddCityActivity.class);
                startActivityForResult(intent, 1);
            }
        };
        ((Button) findViewById(R.id.add_city_button)).setOnClickListener(addListener);
        View.OnClickListener refreshListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrefCityDBHelper.init(ma).updateAllDataFromWeb();
                ma.update();
            }
        };
        ((Button) findViewById(R.id.refresh_button)).setOnClickListener(refreshListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ma.update();
    }

    @Override
    public void update() {
        PrefCitiesListFragment fr = (PrefCitiesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_pref);
        RecyclerView rv = (RecyclerView) fr.getView();
        CityWithTempAdapter cwta = new CityWithTempAdapter(PrefCityDBHelper.init(ma).getCityList(), ma);
        rv.setAdapter(cwta);
        Toast.makeText(ma, "update", Toast.LENGTH_SHORT).show();
    }
}