package com.faa1192.weatherforecast.weather

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

//Активити содержащее сведения о погоде в конкретном городе
class WeatherInfoActivity : AppCompatActivity(), Updatable, OnRefreshListener {
    private var city: City? = null
    private var swipeRefreshLayout: SwipeRefreshLayout? = null

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
        actionBar.setBackgroundDrawable(ColorDrawable(applicationContext.getColor(R.color.col_pr_dark)))
        actionBar.title = Html.fromHtml(
            "<font color=\"" + applicationContext.getColor(R.color.pr_text) + "\">" + resources.getString(
                R.string.weather_in_city
            ) + city!!.shortName + "</font>",
            0
        )
        val upArrow = applicationContext.getDrawable(R.drawable.ic_back_arrow)
        actionBar.setHomeAsUpIndicator(upArrow)
        swipeRefreshLayout = binding.refresherWeatherData
        swipeRefreshLayout!!.setOnRefreshListener(this)
        swipeRefreshLayout!!.setColorSchemeColors(
            Color.argb(255, 200, 0, 0),
            Color.argb(255, 0, 200, 0),
            Color.argb(255, 0, 0, 200)
        )
    }

    //Обновление данных о погоде с инета
    override fun onRefresh() {
        swipeRefreshLayout!!.isRefreshing = true
        customInit(this).updateDataFromWeb(city!!)
        Handler(Looper.myLooper()!!).postDelayed(
            { swipeRefreshLayout!!.isRefreshing = false },
            3000
        )
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
    }
}