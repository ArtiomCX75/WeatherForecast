package com.faa1192.weatherforecast.Weather;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;

import com.faa1192.weatherforecast.Cities.City;
import com.faa1192.weatherforecast.Preferred.PrefCityDBHelper;
import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.Updatable;
import com.faa1192.weatherforecast.databinding.ActivityWeatherInfoBinding;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;

//Активити содержащее сведения о погоде в конкретном городе
public class WeatherInfoActivity extends AppCompatActivity implements Updatable, SwipeRefreshLayout.OnRefreshListener {
    private City city;
    private SwipeRefreshLayout swipeRefreshLayout;
    FirebaseInstanceId firebaseInstanceId = FirebaseInstanceId.getInstance();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("MY", firebaseInstanceId.getToken());
        //  Toast.makeText(WeatherInfoActivity.this, R.string.pull_for_refresh, Toast.LENGTH_SHORT).show();
        ActivityWeatherInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_info);
        Intent intent = getIntent();
        city = City.fromBundle(intent.getExtras());
        final WeatherInfoFragment fragment = (WeatherInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        fragment.setInfo(city);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.col_pr_dark)));
        actionBar.setTitle(Html.fromHtml("<font color=\"" + getResources().getColor(R.color.pr_text) + "\">" + getResources().getString(R.string.weather_in_city) + city.getShortName() + "</font>"));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.pr_text), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        swipeRefreshLayout = binding.refresherWeatherData;
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.argb(255, 200, 0, 0), Color.argb(255, 0, 200, 0), Color.argb(255, 0, 0, 200));
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //Обновление данных о погоде с инета
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        PrefCityDBHelper.init(this).updateDataFromWeb(city);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);

    }

    //Обновление данных о погоде с базы
    @Override
    public void update() {
        final WeatherInfoFragment fragment = (WeatherInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment3);
        fragment.setInfo(PrefCityDBHelper.init(this).getCity(city.id));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("WeatherInfo Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}