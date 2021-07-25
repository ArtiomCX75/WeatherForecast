package com.faa1192.weatherforecast.preferred

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.Updatable
import com.faa1192.weatherforecast.cities.City
import com.faa1192.weatherforecast.cities.CityInListAdapter
import com.faa1192.weatherforecast.weather.WeatherInfoActivity

//адптер для recycler view класса PrefCitiesListFragment
class PrefCitiesAdapter(cities: List<City>?, context: Context?) :
    CityInListAdapter(cities, context!!) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_city_with_temp, parent, false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView
        var textView = cardView.findViewById<View>(R.id.city_wt_name) as TextView
        textView.text = cityList?.get(position)?.shortName
        textView = cardView.findViewById<View>(R.id.city_wt_temp) as TextView
        textView.text = (cityList?.get(position)?.data?.temp ?: "") + ",  " + (cityList?.get(
            position
        )?.data?.weatherMain
            ?: "")
        cardView.findViewWithTag<View>("lin_layout").setOnClickListener {
            val intent = Intent(context, WeatherInfoActivity::class.java)
            val selectedCity = cityList?.get(position)
            if (selectedCity != null) {
                intent.putExtras(selectedCity.toBundle())
            }
            context.startActivity(intent)
            (context as PrefCitiesActivity).overridePendingTransition(
                R.anim.alpha_on,
                R.anim.alpha_off
            )
        }
        cardView.findViewWithTag<View>("lin_layout").setOnLongClickListener {
            val builder = AlertDialog.Builder(context)
            val allertTitle = context.resources.getString(R.string.del_city_title)
            val allerMessage = context.resources.getString(R.string.del_city_question)
            val yes = context.resources.getString(R.string.yes)
            val no = context.resources.getString(R.string.no)
            builder.setTitle(allertTitle)
                .setMessage(String.format(allerMessage, cityList?.get(position)?.shortName))
                .setCancelable(true)
                .setNegativeButton(
                    no
                ) { dialog, id -> dialog.cancel() }
                .setPositiveButton(yes) { dialog, id ->
                    cityList?.get(position)?.let { it1 ->
                        PrefCityDBHelper.customInit(context).delFromDbPref(
                            it1
                        )
                    }
                    try {
                        (context as Updatable).update()
                    } catch (e: Exception) {
                        Log.e("my", "citywithtempadapter: cannot be cast to updatable")
                    }
                    dialog.cancel()
                }
            val alert = builder.create()
            alert.show()
            true
        }
    }
}