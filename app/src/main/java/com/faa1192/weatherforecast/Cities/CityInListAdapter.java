package com.faa1192.weatherforecast.Cities;

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

import com.faa1192.weatherforecast.Preferred.PrefCityDBHelper;
import com.faa1192.weatherforecast.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CityInListAdapter extends RecyclerView.Adapter<CityInListAdapter.ViewHolder> {
    protected final List<City> cities;
    protected final Context context;

    public CityInListAdapter(List<City> cities, Context context) {
        this.cities = cities;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    @Override
    public CityInListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_city_in_list, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.city_info_text);
        textView.setText(cities.get(position).name);
        cardView.findViewWithTag("lin_layout").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                City city = cities.get(position);
                Intent intent = new Intent();
                intent.putExtras(city.toBundle());
                Toast.makeText(context, city.name, Toast.LENGTH_SHORT).show();
                PrefCityDBHelper.init(context).addToDbPref(city);
                PrefCityDBHelper.init(context).updateDataFromWeb(city);
                Activity act = (Activity) context;
                act.setResult(RESULT_OK, intent);
                act.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}