package com.faa1192.weatherforecast.preferred

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.multidex.MultiDex
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.faa1192.weatherforecast.MyObserver
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.Updatable
import com.faa1192.weatherforecast.cities.AddCityActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

//Активити содержащее список городов добавленных в избранное
class PrefCitiesActivity : AppCompatActivity(), Updatable, OnRefreshListener {
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    lateinit var myObserver: MyObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MultiDex.install(this)
        context = applicationContext
        setContentView(R.layout.activity_pref_list)
        myObserver = MyObserver()
        lifecycle.addObserver(myObserver)
        //Toast.makeText(PrefCitiesActivity.this, getResources().getString(R.string.pull_for_refresh), Toast.LENGTH_SHORT).show();
        //Toast.makeText(PrefCitiesActivity.this, getResources().getString(R.string.hold_for_delete), Toast.LENGTH_SHORT).show();
        PrefCityDBHelper.customInit(applicationContext).updateAllDataFromWeb()
        val addListener =
            View.OnClickListener { //Toast.makeText(PrefCitiesActivity.this, getResources().getString(R.string.wait_pls), Toast.LENGTH_SHORT).show();
                val intent = Intent(applicationContext, AddCityActivity::class.java)
                startActivityForResult(intent, 1)
                overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off)
            }
        val actionBar = supportActionBar
        actionBar!!.setTitle(R.string.favorites)
        actionBar.setBackgroundDrawable(ColorDrawable(applicationContext.getColor(R.color.col_pr_dark)))
        actionBar.title = Html.fromHtml(
            "<font color=\"" + applicationContext.getColor(R.color.pr_text) + "\">" + getString(R.string.app_name) + "</font>",
            0
        )
        val upArrow = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_back_arrow)
        actionBar.setHomeAsUpIndicator(upArrow)
        val floatingActionButton = findViewById<View>(R.id.fabpref) as FloatingActionButton
        floatingActionButton.backgroundTintList =
            ColorStateList.valueOf(applicationContext.getColor(R.color.col_pr))
        floatingActionButton.setOnClickListener(addListener)
        floatingActionButton.size = FloatingActionButton.SIZE_NORMAL
        floatingActionButton.setColorFilter(applicationContext.getColor(R.color.sec_text))
        swipeRefreshLayout = findViewById<View>(R.id.refresher) as SwipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener(this)
        swipeRefreshLayout!!.setColorSchemeColors(
            Color.argb(255, 200, 0, 0),
            Color.argb(255, 0, 200, 0),
            Color.argb(255, 0, 0, 200)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // if (resultCode != -1)
        //     return;
        //   City city = City.fromBundle(data.getExtras());
        //   Toast.makeText(this, getResources().getString((R.string.added_city)) + city.name, Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data)
        update()
    }

    //Обновление адаптера при добавлении нового города, удалении из избранного etc
    override fun update() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.fragment_pref) as PrefCitiesListFragment?
        val recyclerView = fragment!!.view as RecyclerView?
        val prefCitiesAdapter = PrefCitiesAdapter(
            PrefCityDBHelper.customInit(this@PrefCitiesActivity).cityList,
            this@PrefCitiesActivity
        )
        recyclerView!!.adapter = prefCitiesAdapter
    }

    //Обновление данных о погоде (с инета)
    override fun onRefresh() {
        swipeRefreshLayout!!.isRefreshing = true
        PrefCityDBHelper.customInit(this@PrefCitiesActivity).updateAllDataFromWeb()
        update()
        Handler(Looper.myLooper()!!).postDelayed(
            { swipeRefreshLayout!!.isRefreshing = false },
            3000
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off)
    }

    override fun onResume() {
        super.onResume()
        update()
    }

    companion object {
        @JvmField
        var context: Context? = null
    }
}