package com.faa1192.weatherforecast.Countries;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faa1192.weatherforecast.Cities.City;
import com.faa1192.weatherforecast.Preferred.PrefCityDBHelper;
import com.faa1192.weatherforecast.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

//адптер для recycler view класса CountriesListFragment
public class CountryInListAdapter extends RecyclerView.Adapter<CountryInListAdapter.ViewHolder> {
    private final List<String> countriesList;
    private final Context context;

    public CountryInListAdapter(List<String> countriesList, Context context) {
        this.countriesList = countriesList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    @Override
    public CountryInListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_country_in_list, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        TextView countyTextView = (TextView) cardView.findViewById(R.id.country_info_text);
        countyTextView.setText(countriesList.get(position));
        Log.e("my", "COUNTRY "+countriesList.get(position));
        cardView.findViewWithTag("lin_layout").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chosenCountry = countriesList.get(position);
                Intent intent = new Intent();
                intent.putExtra("country", chosenCountry);
                Activity activity = (Activity) context;
                activity.setResult(RESULT_OK, intent);
                activity.finish();
                ((CountriesActivity) context).overridePendingTransition(R.anim.alpha_on,R.anim.alpha_off);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }
}