package com.faa1192.weatherforecast.countries

import android.content.res.Configuration
import android.os.Bundle
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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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
        CoroutineScope(Dispatchers.IO).launch {
            downloadCountyList()
        }
          list.add("RU");
          list.add("UA");
        return recyclerView
    }

    fun update() {
/*        CoroutineScope(Dispatchers.IO).launch {
            downloadCountyList()
        } */
    }

    private fun downloadCountyList() {
        if (list.isNotEmpty()) {
            return
        }
        var success = false
        list.clear()

        val mURL =
            URL("https://raw.githubusercontent.com/ArtiomCX75/WeatherForecast/develop/citieslist/list.txt")

        with(mURL.openConnection() as HttpURLConnection) {
            requestMethod = "GET"
            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()
                var inputLine = it.readLine()
                while (inputLine != null) {
                    list.add(inputLine)
                    response.append(inputLine)
                    inputLine = it.readLine()
                    success = true
                }
                it.close()
            }
        }

        try {
            if (success) {
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
        } catch (_: Exception) {}

    }
}