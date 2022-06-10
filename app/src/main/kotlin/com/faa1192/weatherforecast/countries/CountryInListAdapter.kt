package com.faa1192.weatherforecast.countries

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.cities.CityDBHelper.Companion.init

//адптер для recycler view класса CountriesListFragment
class CountryInListAdapter(private val countriesList: List<String>, private val context: Context) :
    RecyclerView.Adapter<CountryInListAdapter.ViewHolder>() {
    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_country_in_list, parent, false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView
        val countyTextView = cardView.findViewById<View>(R.id.country_info_text) as TextView
        countyTextView.text = Country(context).getName(countriesList[position])
        Log.e("my", "COUNTRY " + countriesList[position])
        cardView.findViewWithTag<View>("lin_layout").setOnClickListener {
            val progressBar =
                (context as CountriesActivity).findViewById<View>(R.id.progressBar) as ProgressBar
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            progressBar.layoutParams = lp
            val chosenCountry = countriesList[position]
            // Intent intent = new Intent();
            val activity = context as Activity
            activity.setResult(Activity.RESULT_OK)
            //String country = data.getStringExtra("country");
            Toast.makeText(
                context,
                context.getResources().getString(R.string.downloading_5_min),
                Toast.LENGTH_SHORT
            ).show()
            val builder = AlertDialog.Builder(
                context
            )
            builder.setTitle(context.getResources().getString(R.string.downloading))
                .setMessage(context.getResources().getString(R.string.wait_pls))
                .setCancelable(false)
            val alert = builder.create()
            alert.show()
            init(context).downloadCitiesOfCountry(chosenCountry)
        }
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }
}