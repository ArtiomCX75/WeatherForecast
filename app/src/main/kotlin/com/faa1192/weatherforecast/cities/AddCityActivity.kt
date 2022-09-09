package com.faa1192.weatherforecast.cities

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.SearchView
import androidx.multidex.MultiDex
import androidx.recyclerview.widget.RecyclerView
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.Updatable
import com.faa1192.weatherforecast.countries.CountriesActivity
import com.faa1192.weatherforecast.databinding.ActivityAddCityBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

//Активити содержащее список всех городов
class AddCityActivity : AppCompatActivity(), Updatable {
    private lateinit var binding: ActivityAddCityBinding
    private var searchView: SearchView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MultiDex.install(this)
        binding = ActivityAddCityBinding.inflate(layoutInflater)
        setContentView(binding.addCityLayout)
        val actionBar = supportActionBar
        actionBar!!.setBackgroundDrawable(ColorDrawable(applicationContext.getColor(R.color.col_pr_dark)))
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.title = Html.fromHtml(
            "<font color=\"" + applicationContext.getColor(R.color.pr_text) + "\">" + getString(R.string.adding_city) + "</font>",
            0
        )
        val upArrow = AppCompatResources.getDrawable(applicationContext, R.drawable.ic_back_arrow)

        // TODO fix me        upArrow?.setColorFilter(applicationContext.getColor(R.color.pr_text), PorterDuff.Mode.SRC_ATOP)
        actionBar.setHomeAsUpIndicator(upArrow)
        searchView = binding.searchView
        searchView!!.setIconifiedByDefault(false)
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                update()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                update()
                return false
            }
        })
        searchView!!.isFocusable = false
        val addListener =
            View.OnClickListener { // Toast.makeText(AddCityActivity.this, getResources().getString(R.string.wait_pls), Toast.LENGTH_SHORT).show();
                val intent = Intent(applicationContext, CountriesActivity::class.java)
                startActivityForResult(intent, 1)
                overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off)
            }
        val fab = binding.fabcity
        fab.backgroundTintList =
            ColorStateList.valueOf(applicationContext.getColor(R.color.col_pr))
        fab.setOnClickListener(addListener)
        fab.size = FloatingActionButton.SIZE_NORMAL
        fab.setColorFilter(applicationContext.getColor(R.color.sec_text))
    }

    fun onActivityResult(result: ActivityResult) {
        if (result.resultCode != -1) return
        update()
    }

    //Обновление адаптера во время поиска
    override fun update() {
        val citiesListFragment = binding.fragmentCityList
        val rv = citiesListFragment as RecyclerView?
        val cityInListAdapter =
            CityInListAdapter(
                CityDBHelper.init(this@AddCityActivity).getCityList(
                    searchView!!.query.toString().trim { it <= ' ' }), this@AddCityActivity
            )
        rv!!.adapter = cityInListAdapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off)
    }
}