package com.faa1192.weatherforecast;

import android.content.Context;
import android.support.v7.widget.CardView;
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

public class CityWithExtraDataAdapter extends CityInListAdapter {

    public CityWithExtraDataAdapter(List<City> cities, Context context) {
        super(cities, context);
    }
    @Override
    public CityInListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_city_with_extra_data, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        CardView cardView = holder.cardView;
        ((TextView) cardView.findViewById(R.id.extra_city_name)).setText("name: "+cities.get(position).data.cityName);
        ((TextView) cardView.findViewById(R.id.extra_city_humidity)).setText("humidity: "+cities.get(position).data.humidity);
        ((TextView) cardView.findViewById(R.id.extra_city_pressure)).setText("pressure: "+cities.get(position).data.pressure);
        ((TextView) cardView.findViewById(R.id.extra_city_temp)).setText("temperature: "+cities.get(position).data.temp);
        ((TextView) cardView.findViewById(R.id.extra_city_sunrise)).setText("sunrise: "+cities.get(position).data.sunrise);
        ((TextView) cardView.findViewById(R.id.extra_city_sunset)).setText("sunset: "+cities.get(position).data.sunset);
        ((TextView) cardView.findViewById(R.id.extra_city_wind_speed)).setText("wind speed: "+cities.get(position).data.windSpeed);
        ((TextView) cardView.findViewById(R.id.extra_city_wind_deg)).setText("wind direction: "+cities.get(position).data.windDeg);
        ((TextView) cardView.findViewById(R.id.extra_city_weather_description)).setText("weather description: "+cities.get(position).data.weatherDescription);
        ((TextView) cardView.findViewById(R.id.extra_city_weather_main)).setText("weather main: "+cities.get(position).data.weatherMain);
        ((TextView) cardView.findViewById(R.id.extra_city_time)).setText("time: "+Integer.valueOf(cities.get(position).data.time).toString());

        ((LinearLayout) cardView.findViewWithTag("lin_layout")).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "t: "+cities.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
