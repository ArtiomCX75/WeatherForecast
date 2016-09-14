package com.faa1192.weatherforecast.Preferred;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.faa1192.weatherforecast.Cities.AddCityActivity;
import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.Updatable;

//Активити содержащее список городов добавленных в избранное
public class PrefCitiesActivity extends AppCompatActivity implements Updatable, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();
        setContentView(R.layout.activity_pref_list);
        //Toast.makeText(PrefCitiesActivity.this, getResources().getString(R.string.pull_for_refresh), Toast.LENGTH_SHORT).show();
        //Toast.makeText(PrefCitiesActivity.this, getResources().getString(R.string.hold_for_delete), Toast.LENGTH_SHORT).show();
        PrefCityDBHelper.init(getApplicationContext()).updateAllDataFromWeb();
        View.OnClickListener addListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(PrefCitiesActivity.this, getResources().getString(R.string.wait_pls), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AddCityActivity.class);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off);
            }
        };

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.favorites);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.col_pr_dark)));
        actionBar.setTitle(Html.fromHtml("<font color=\"" + getResources().getColor(R.color.pr_text) + "\">" + getString(R.string.app_name) + "</font>"));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.pr_text), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fabpref);
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.col_pr)));
        floatingActionButton.setOnClickListener(addListener);
        floatingActionButton.setSize(FloatingActionButton.SIZE_NORMAL);
        floatingActionButton.setColorFilter(getResources().getColor(R.color.sec_text));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresher);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.argb(255, 200, 0, 0), Color.argb(255, 0, 200, 0), Color.argb(255, 0, 0, 200));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // if (resultCode != -1)
       //     return;
        //   City city = City.fromBundle(data.getExtras());
        //   Toast.makeText(this, getResources().getString((R.string.added_city)) + city.name, Toast.LENGTH_SHORT).show();
        PrefCitiesActivity.this.update();
    }

    //Обновление адаптера при добавлении нового города, удалении из избранного etc
    @Override
    public void update() {
        PrefCitiesListFragment fragment = (PrefCitiesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_pref);
        RecyclerView recyclerView = (RecyclerView) fragment.getView();
        PrefCitiesAdapter prefCitiesAdapter = new PrefCitiesAdapter(PrefCityDBHelper.init(PrefCitiesActivity.this).getCityList(), PrefCitiesActivity.this);
        recyclerView.setAdapter(prefCitiesAdapter);
    }

    //Обновление данных о погоде (с инета)
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        PrefCityDBHelper.init(PrefCitiesActivity.this).updateAllDataFromWeb();
        PrefCitiesActivity.this.update();
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

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }
}