package com.faa1192.weatherforecast.countries

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.faa1192.weatherforecast.databinding.ActivityCountriesBinding
import com.faa1192.weatherforecast.databinding.RecycleViewBinding
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
    private lateinit var activityCountriesBinding: ActivityCountriesBinding

    private lateinit var binding: RecycleViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecycleViewBinding.inflate(inflater)
        activityCountriesBinding = ActivityCountriesBinding.inflate(inflater)
        val recyclerView = binding.recycleViewCities

        update()
        //  list.add("RU");
        //   list.add("UA");
        return recyclerView
    }

    fun update() {
        Log.d("WWW", "update 0")
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("WWW", "update 1")
            downloadCountyList()
            Log.d("WWW", "update 2")
        }
    }

    private fun downloadCountyList() {
        var success: Boolean
        list.clear()
        try {
            Log.d("WWW", "try")
            val okHttpClient = OkHttpClient()
            val request: Request = Request.Builder().get()
                .url("https://raw.githubusercontent.com/ArtiomCX75/WeatherForecast/develop/citieslist/list.txt")
                .build()
            val response = okHttpClient.newCall(request).execute()
            val br = BufferedReader(
                Objects.requireNonNull(response.body).charStream()
            )
            var temp = br.readLine()
            while (temp != null && temp.isNotEmpty()) {
                list.add(temp)
                temp = br.readLine()
            }
            success = true
            Log.d("WWW", "success true")
        } catch (e: IOException) {
            Log.d("WWW", "success false")
            success = false
            e.printStackTrace()
        }

        try {
            Log.d("WWW", "try 2")
            if (success) {
                Log.d("WWW", "success 2")
                val countryInListAdapter = context?.let { CountryInListAdapter(list, it) }

                val recyclerView = binding.recycleViewCities
                val orientation = requireActivity().resources.configuration.orientation
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    val linearLayoutManager = LinearLayoutManager(activity)
                    linearLayoutManager.isSmoothScrollbarEnabled = true
                    recyclerView.layoutManager = linearLayoutManager
                } else {
                    val staggeredGridLayoutManager =
                        StaggeredGridLayoutManager(2, Configuration.ORIENTATION_PORTRAIT)
                    recyclerView.layoutManager = staggeredGridLayoutManager
                }
                recyclerView.adapter = countryInListAdapter
            }
        } catch (_: Exception) {
        }

    }
}