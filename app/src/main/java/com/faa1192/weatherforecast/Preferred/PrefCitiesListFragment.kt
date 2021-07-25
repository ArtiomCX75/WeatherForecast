package com.faa1192.weatherforecast.preferred

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.cities.AddCityActivity

//Фрагмент со списком избранных городов
class PrefCitiesListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.recycle_view, container, false) as RecyclerView
        val prefCitiesAdapter =
            PrefCitiesAdapter(context?.let { PrefCityDBHelper.customInit(it).cityList }, context)
        val prefCityCount = prefCitiesAdapter.itemCount
        if (prefCityCount == 0) {
            requireActivity().startActivityForResult(
                Intent(activity, AddCityActivity::class.java),
                1
            )
            requireActivity().overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off)
        }
        recyclerView.adapter = prefCitiesAdapter
        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager
        val simpleCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Toast.makeText(activity, "mv: $target=$viewHolder", Toast.LENGTH_LONG).show()
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //  Toast.makeText(getActivity(), "sw: "+direction+"="+viewHolder, Toast.LENGTH_LONG).show();
                val fragment =
                    activity!!.supportFragmentManager.findFragmentById(R.id.fragment_pref) as PrefCitiesListFragment?
                val recyclerView = fragment!!.view as RecyclerView?
                var cwta = recyclerView!!.adapter as PrefCitiesAdapter?
                cwta!!.cityList?.get(viewHolder.adapterPosition)?.let {
                    PrefCityDBHelper.customInit(activity!!)
                        .delFromDbPref(it)
                }
                cwta = PrefCitiesAdapter(PrefCityDBHelper.customInit(activity!!).cityList, activity)
                recyclerView.adapter = cwta
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        return recyclerView
    }
}