package com.faa1192.weatherforecast.cities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.countries.CountriesActivity
import com.faa1192.weatherforecast.databinding.RecycleViewBinding

//Фрагмент со списком всех городов
class CitiesListFragment : Fragment() {
    private lateinit var binding: RecycleViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecycleViewBinding.inflate(inflater)
        val recyclerView = binding.recycleViewCities
        val cityInListAdapter =
            context?.let { CityInListAdapter(CityDBHelper.init(activity).getCityList(""), it) }
        val cityCount = cityInListAdapter?.itemCount
        if (cityCount == 0) {
            requireActivity().startActivityForResult(
                Intent(
                    activity,
                    CountriesActivity::class.java
                ), 1
            )
            requireActivity().overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off)
        }
        recyclerView.adapter = cityInListAdapter
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
        return recyclerView
    }
}