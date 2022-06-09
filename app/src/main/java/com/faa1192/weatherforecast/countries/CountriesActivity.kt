package com.faa1192.weatherforecast.countries

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.multidex.MultiDex
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.Updatable
import com.faa1192.weatherforecast.databinding.ActivityCountriesBinding

//Активити содержащее список всех стран
class CountriesActivity : AppCompatActivity(), Updatable, OnRefreshListener {
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var binding: ActivityCountriesBinding? = null
    private val screenOrientation: Int
        get() {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            return -1
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MultiDex.install(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_countries)
        //        setContentView(R.layout.activity_countries);
        requestedOrientation =
            if (screenOrientation == 0) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val actionBar = supportActionBar
        actionBar!!.setBackgroundDrawable(ColorDrawable(applicationContext.getColor(R.color.col_pr_dark)))
        actionBar.title = Html.fromHtml(
            "<font color=\"" + applicationContext.getColor(R.color.pr_text) + "\">" + getString(
                R.string.adding_cities
            ) + "</font>", 0
        )
        actionBar.setDisplayHomeAsUpEnabled(true)
        val upArrow = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_back_arrow)
        upArrow?.setColorFilter(
            applicationContext.getColor(R.color.pr_text),
            PorterDuff.Mode.SRC_ATOP
        )
        actionBar.setHomeAsUpIndicator(upArrow)
        swipeRefreshLayout = (binding as ActivityCountriesBinding).refresher
        swipeRefreshLayout!!.setOnRefreshListener(this)
        swipeRefreshLayout!!.setColorSchemeColors(
            Color.argb(255, 200, 0, 0),
            Color.argb(255, 0, 200, 0),
            Color.argb(255, 0, 0, 200)
        )
        val progressBar = (binding as ActivityCountriesBinding).progressBar
        progressBar.progressDrawable.setColorFilter(
            applicationContext.getColor(R.color.pr_text),
            PorterDuff.Mode.SRC_IN
        )
    }

    override fun update() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.fragment_coun) as CountriesListFragment?
        //  RecyclerView recyclerView = (RecyclerView) fragment.getView();
        fragment!!.update()
    }

    override fun onRefresh() {
        swipeRefreshLayout!!.isRefreshing = true
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

    override fun onStop() {
        super.onStop()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onStart() {
        super.onStart()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}