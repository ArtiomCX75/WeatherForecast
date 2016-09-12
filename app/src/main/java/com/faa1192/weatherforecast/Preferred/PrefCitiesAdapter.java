package com.faa1192.weatherforecast.Preferred;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faa1192.weatherforecast.Cities.City;
import com.faa1192.weatherforecast.Cities.CityInListAdapter;
import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.Updatable;
import com.faa1192.weatherforecast.Weather.WeatherInfoActivity;

import java.util.List;

//адптер для recycler view класса PrefCitiesListFragment
public class PrefCitiesAdapter extends CityInListAdapter {


    public PrefCitiesAdapter(List<City> cities, Context context) {
        super(cities, context);
    }

    @Override
    public CityInListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_city_with_temp, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.city_wt_name);
        textView.setText(cityList.get(position).getShortName());
        textView = (TextView) cardView.findViewById(R.id.city_wt_temp);
        textView.setText(cityList.get(position).data.getTemp() + ",  " + cityList.get(position).data.getWeatherMain());
        cardView.findViewWithTag("lin_layout").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WeatherInfoActivity.class);
                City selectedCity = cityList.get(position);
                intent.putExtras(selectedCity.toBundle());
                context.startActivity(intent);
                ((PrefCitiesActivity) context).overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off);
            }
        });
        cardView.findViewWithTag("lin_layout").setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String allertTitle = context.getResources().getString(R.string.del_city_title);
                String allerMessage = context.getResources().getString(R.string.del_city_question);
                String yes = context.getResources().getString(R.string.yes);
                String no = context.getResources().getString(R.string.no);
                builder.setTitle(allertTitle)
                        .setMessage(String.format(allerMessage, cityList.get(position).getShortName()))
                        .setCancelable(true)
                        .setNegativeButton(no,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }).setPositiveButton(yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PrefCityDBHelper.init(context).delFromDbPref(cityList.get(position));
                        try {
                            ((Updatable) context).update();
                        } catch (Exception e) {
                            Log.e("my", "citywithtempadapter: cannot be cast to updatable");
                        }
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });
    }
}