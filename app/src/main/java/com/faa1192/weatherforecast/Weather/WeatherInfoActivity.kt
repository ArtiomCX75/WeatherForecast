package com.faa1192.weatherforecast.weather

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.multidex.MultiDex
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.Updatable
import com.faa1192.weatherforecast.cities.City
import com.faa1192.weatherforecast.cities.City.Companion.fromBundle
import com.faa1192.weatherforecast.databinding.ActivityWeatherInfoBinding
import com.faa1192.weatherforecast.preferred.PrefCityDBHelper.Companion.customInit
import com.google.android.gms.appindexing.Action
import com.google.android.gms.appindexing.AppIndex
import com.google.android.gms.appindexing.Thing
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.iid.FirebaseInstanceId

//Активити содержащее сведения о погоде в конкретном городе
class WeatherInfoActivity : AppCompatActivity(), Updatable, OnRefreshListener {
    private var city: City? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    var firebaseInstanceId = FirebaseInstanceId.getInstance()

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private var client: GoogleApiClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        MultiDex.install(this)
        super.onCreate(savedInstanceState)
        //  Toast.makeText(WeatherInfoActivity.this, R.string.pull_for_refresh, Toast.LENGTH_SHORT).show();
        val binding: ActivityWeatherInfoBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_weather_info)
        val intent = intent
        city = fromBundle(intent.extras!!)
        val fragment =
            supportFragmentManager.findFragmentById(R.id.fragment3) as WeatherInfoFragment?
        fragment!!.setInfo(city!!)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.col_pr_dark)))
        actionBar.title = Html.fromHtml(
            "<font color=\"" + resources.getColor(R.color.pr_text) + "\">" + resources.getString(R.string.weather_in_city) + city!!.shortName + "</font>"
        )
        val upArrow = resources.getDrawable(R.drawable.ic_back_arrow)
        upArrow.setColorFilter(resources.getColor(R.color.pr_text), PorterDuff.Mode.SRC_ATOP)
        actionBar.setHomeAsUpIndicator(upArrow)
        swipeRefreshLayout = binding.refresherWeatherData
        swipeRefreshLayout!!.setOnRefreshListener(this)
        swipeRefreshLayout!!.setColorSchemeColors(
            Color.argb(255, 200, 0, 0),
            Color.argb(255, 0, 200, 0),
            Color.argb(255, 0, 0, 200)
        )
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = GoogleApiClient.Builder(this).addApi(AppIndex.API).build()
    }

    //Обновление данных о погоде с инета
    override fun onRefresh() {
        swipeRefreshLayout!!.isRefreshing = true
        customInit(this).updateDataFromWeb(city!!)
        Handler().postDelayed({ swipeRefreshLayout!!.isRefreshing = false }, 3000)
    }

    //Обновление данных о погоде с базы
    override fun update() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.fragment3) as WeatherInfoFragment?
        fragment!!.setInfo(customInit(this).getCity(city!!.id))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off)
    }// TODO: Define a title for the content shown.
    // TODO: Make sure this auto-generated URL is correct.
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    val indexApiAction: Action
        get() {
            val `object` = Thing.Builder()
                .setName("WeatherInfo Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build()
            return Action.Builder(Action.TYPE_VIEW)
                .setObject(`object`)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build()
        }

    public override fun onStart() {
        super.onStart()

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client!!.connect()
        AppIndex.AppIndexApi.start(client!!, indexApiAction)
    }

    public override fun onStop() {
        super.onStop()

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client!!, indexApiAction)
        client!!.disconnect()
    }
}