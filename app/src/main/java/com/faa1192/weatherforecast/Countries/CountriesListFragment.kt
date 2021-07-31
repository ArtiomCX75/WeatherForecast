package com.faa1192.weatherforecast.countries

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.faa1192.weatherforecast.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.IOException
import java.util.*

//Фрагмент со списком стран
class CountriesListFragment  //   https://raw.githubusercontent.com/ArtiomCX75/WeatherForecast/move_citieslist_to_server/citieslist/list.txt
    : Fragment() {
    private val list = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recyclerView = inflater.inflate(R.layout.recycle_view, container, false) as RecyclerView
        update()
        //  list.add("RU");
        //   list.add("UA");
        return recyclerView
    }

    fun update() {
        CoroutineScope(Dispatchers.IO).launch {
            downloadCountyList()
        }
    }

    private fun downloadCountyList() {
        var success: Boolean
        list.clear()
        try {
            val okHttpClient = OkHttpClient()
            val request: Request = Request.Builder().get()
                .url("https://raw.githubusercontent.com/ArtiomCX75/WeatherForecast/develop/citieslist/list.txt")
                .build()
            val response = okHttpClient.newCall(request).execute()
            val br = BufferedReader(
                Objects.requireNonNull(response.body)?.charStream()
            )
            var temp = br.readLine()
            while (temp != null && temp.isNotEmpty()) {
                list.add(temp)
                temp = br.readLine()
            }
            success = true
        } catch (e: IOException) {
            success = false
            e.printStackTrace()
        }

        try {
            if (success) {
                val countryInListAdapter = context?.let { CountryInListAdapter(list, it) }
                val clf =
                    activity!!.supportFragmentManager.findFragmentById(R.id.fragment_coun) as CountriesListFragment?
                val recyclerView = clf!!.view as RecyclerView?
                val orientation = activity!!.resources.configuration.orientation
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    val linearLayoutManager = LinearLayoutManager(activity)
                    linearLayoutManager.isSmoothScrollbarEnabled = true
                    recyclerView!!.layoutManager = linearLayoutManager
                } else {
                    val staggeredGridLayoutManager =
                        StaggeredGridLayoutManager(2, Configuration.ORIENTATION_PORTRAIT)
                    recyclerView!!.layoutManager = staggeredGridLayoutManager
                }
                recyclerView.adapter = countryInListAdapter
            }
        } catch (e: Exception) {
        }

    }
}