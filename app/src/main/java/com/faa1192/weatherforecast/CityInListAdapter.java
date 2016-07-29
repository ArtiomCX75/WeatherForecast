package com.faa1192.weatherforecast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by faa11 on 12.07.2016.
 */

public class CityInListAdapter extends RecyclerView.Adapter<CityInListAdapter.ViewHolder>{
    public List<City> cities;
    public Context context;
    public CityInListAdapter(List<City> cities, Context context){
        Log.e("MY", "create city in list  adapter");
        this.cities = cities;
        this.context = context;
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
        final CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.city_info_text);
        textView.setText(cities.get(position).name);
        ((LinearLayout) cardView.findViewWithTag("lin_layout")).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtras(cities.get(position).toBundle());
                Toast.makeText(context, City.fromBundle(intent.getExtras()).name, Toast.LENGTH_SHORT).show();
                PrefCityDBHelper.init(context).addToDbPref(cities.get(position));
                PrefCityDBHelper.init(context).updateDataFromWeb(cities.get(position));
                Activity act = (Activity) context;
                act.setResult(RESULT_OK, intent);
                act.finish();
            }
        });
    }

    @Override
    public int getItemCount(){
        return cities.size();
    }
}