package com.faa1192.weatherforecast;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by faa11 on 12.07.2016.
 */

public class CityWeatherAdapter  extends RecyclerView.Adapter<CityWeatherAdapter.ViewHolder>{
    private List<String> cities;
    private int temperatures;

    public CityWeatherAdapter(List<String> cities, int temperatures){
        Log.e("MY", "create adapter");
        this.cities = cities;
        this.temperatures = temperatures;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }
    @Override
    public CityWeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_city_in_list, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Log.e("MY", "!!!added "+position+"   ");
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.city_info_text);
        textView.setText(cities.get(position));
    }

    @Override
    public int getItemCount(){
        return cities.size();
    }


}
