package com.faa1192.weatherforecast.Cities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.faa1192.weatherforecast.Countries.Country;
import com.faa1192.weatherforecast.Preferred.PrefCityDBHelper;
import com.faa1192.weatherforecast.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

//адптер для recycler view класса CitiesListFragment
public class CityInListAdapter extends RecyclerView.Adapter<CityInListAdapter.ViewHolder> {
    public final List<City> cityList;
    protected final Context context;

    public CityInListAdapter(List<City> cityList, Context context) {
        this.cityList = cityList;
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
    public CityInListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_city_in_list, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.city_in_list_name);
        textView.setText(cityList.get(position).getShortName());//country + ": " + cityList.get(position).name);
        textView = (TextView) cardView.findViewById(R.id.city_in_list_name_extra);
        textView.setText((cityList.get(position).getExtraName().length()==0)?(new Country(context).getName(cityList.get(position).country)):(cityList.get(position).getExtraName()+", "+new Country(context).getName(cityList.get(position).country)));
        cardView.findViewWithTag("lin_layout").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                City chosenCity = cityList.get(position);
                PrefCityDBHelper.init(context).addToDbPref(chosenCity);
                PrefCityDBHelper.init(context).updateDataFromWeb(chosenCity);
                Toast.makeText(context, context.getResources().getString((R.string.added_city)) + chosenCity.name, Toast.LENGTH_SHORT).show();
                Activity activity = (Activity) context;
                activity.setResult(RESULT_OK);
                activity.finish();
                activity.overridePendingTransition(R.anim.alpha_on,R.anim.alpha_off);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }
}