package com.faa1192.weatherforecast.cities

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.countries.Country
import com.faa1192.weatherforecast.preferred.PrefCityDBHelper

//адптер для recycler view класса CitiesListFragment
open class CityInListAdapter(val cityList: List<City>?, protected val context: Context) :
    RecyclerView.Adapter<CityInListAdapter.ViewHolder>() {
    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_city_in_list, parent, false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView
        var textView = cardView.findViewById<View>(R.id.city_in_list_name) as TextView
        textView.text =
            cityList?.get(position)?.shortName  //country + ": " + cityList.get(position).name);
        textView = cardView.findViewById<View>(R.id.city_in_list_name_extra) as TextView
        textView.text =
            if (cityList?.get(position)?.extraName?.length == 0) cityList[position].country?.let {
                Country(context).getName(
                    it
                )
            } else cityList?.get(position)?.extraName + ", " + cityList?.get(position)?.country?.let {
                Country(
                    context
                ).getName(it)
            }
        cardView.findViewWithTag<View>("lin_layout").setOnClickListener {
            val chosenCity = cityList?.get(position)
            PrefCityDBHelper.customInit(context).addToDbPref(chosenCity!!)
            PrefCityDBHelper.customInit(context).updateDataFromWeb(chosenCity)
            Toast.makeText(
                context,
                context.resources.getString(R.string.added_city) + chosenCity.shortName,
                Toast.LENGTH_SHORT
            ).show()
            val activity = context as Activity
            activity.setResult(Activity.RESULT_OK)
            activity.finish()
            activity.overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off)
        }
    }

    override fun getItemCount(): Int {
        return cityList!!.size
    }
}