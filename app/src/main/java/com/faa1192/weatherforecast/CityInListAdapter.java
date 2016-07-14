package com.faa1192.weatherforecast;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by faa11 on 12.07.2016.
 */

public class CityInListAdapter extends RecyclerView.Adapter<CityInListAdapter.ViewHolder>{
    public List<String> cities;
    public List<WeatherData> wd;
    public CityInListAdapter(List<String> cities, List<WeatherData> wd){
        Log.e("MY", "create city in list  adapter");
        this.cities = cities;
        this.wd = wd;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }
    @Override
    public CityInListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_city_in_list, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        Log.e("MY", "!!!added "+position+"   ");
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.city_info_text);
        textView.setText(cities.get(position));
        ((LinearLayout) cardView.findViewWithTag("lin_layout")).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "t: "+cities.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount(){
        return cities.size();
    }


}
