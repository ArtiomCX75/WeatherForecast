package com.faa1192.weatherforecast.Countries;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.faa1192.weatherforecast.Cities.CityDBHelper;
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

        final TextView countyTextView = (TextView) cardView.findViewById(R.id.country_info_text);
        countyTextView.setText(new Country(context).getName(countriesList.get(position)));
        Log.e("my", "COUNTRY " + countriesList.get(position));
        cardView.findViewWithTag("lin_layout").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBar progressBar = (ProgressBar) ((CountriesActivity) context).findViewById(R.id.progressBar);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                progressBar.setLayoutParams(lp);
                String chosenCountry = countriesList.get(position);
                // Intent intent = new Intent();
                Activity activity = (Activity) context;
                activity.setResult(RESULT_OK);
                //String country = data.getStringExtra("country");
                Toast.makeText(context, context.getResources().getString(R.string.downloading_5_min), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getResources().getString(R.string.downloading))
                        .setMessage(context.getResources().getString(R.string.wait_pls))
                        .setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();
                CityDBHelper.init(context).downloadCountry(chosenCountry);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countriesList.size();
    }
}