package com.faa1192.weatherforecast;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by faa11 on 14.07.2016.
 */

public class CityWithTempAdapter extends CityInListAdapter {


    public CityWithTempAdapter(List<City> cities, Context context) {
        super(cities, context);
    }

    @Override
    public CityInListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_city_with_temp, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        Log.e("MY", "!!!added "+position+"   ");
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.city_info_text);
        textView.setText(cities.get(position).name);
        textView = (TextView) cardView.findViewById(R.id.city_info_temp);
        textView.setText("t="+cities.get(position).data.getTemp());
        ((LinearLayout) cardView.findViewWithTag("lin_layout")).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "t: "+cities.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, WeatherInfoContainer.class);
                City selectedCity =  cities.get(position);
                intent.putExtras(selectedCity.toBundle());
                context.startActivity(intent);
            }
        });
        ((LinearLayout) cardView.findViewWithTag("lin_layout")).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PrefCityDBHelper.init(context).delFromDbPref(cities.get(position));
                try{
                    ((Updatable) context).update();
                }
                catch (Exception e){
                    Log.e("my", "citywithtempadapter: cannot be cast to updatable");
                }
                return true;
            }
        });
    }
}