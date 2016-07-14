package com.faa1192.weatherforecast;

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


    public CityWithTempAdapter(List<String> cities, List<WeatherData> wd) {
        super(cities, wd);
    }

    @Override
    public CityInListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_city_with_temp, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        Log.e("MY", "!!!added "+position+"   ");
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.city_info_text);
        textView.setText(cities.get(position));
        textView = (TextView) cardView.findViewById(R.id.city_info_temp);
        textView.setText("t="+wd.get(position).temp);
        ((LinearLayout) cardView.findViewWithTag("lin_layout")).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "t: "+cities.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
