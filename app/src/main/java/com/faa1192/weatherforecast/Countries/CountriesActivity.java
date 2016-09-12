package com.faa1192.weatherforecast.Countries;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.Updatable;

//Активити содержащее список всех стран
public class CountriesActivity extends AppCompatActivity implements Updatable, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;

    private int getScreenOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        setRequestedOrientation(((getScreenOrientation() == 0) ? (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) : (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.col_pr_dark)));
        actionBar.setTitle(Html.fromHtml("<font color=\"" + getResources().getColor(R.color.pr_text) + "\">" + getString(R.string.adding_cities) + "</font>"));
        actionBar.setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.pr_text), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresher);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.argb(255, 255, 0, 0), Color.argb(255, 255, 100, 0), Color.argb(255, 255, 0, 100), Color.argb(255, 255, 100, 100));
    }

    @Override
    public void update() {
        CountriesListFragment fragment = (CountriesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_coun);
        RecyclerView recyclerView = (RecyclerView) fragment.getView();
        fragment.update();
        // CountryInListAdapter countryInListAdapter = new CountryInListAdapter(PrefCityDBHelper.init(PrefCitiesActivity.this).getCityList(), PrefCitiesActivity.this);
        //recyclerView.setAdapter(cityWithTempAdapter);
    }

    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        update();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off);
    }
}
