package com.faa1192.weatherforecast.Countries;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faa1192.weatherforecast.R;

import java.util.ArrayList;
//Фрагмент со списком стран
public class CountriesListFragment extends Fragment {
    //   https://raw.githubusercontent.com/ArtiomCX75/WeatherForecast/move_citieslist_to_server/citieslist/list.txt

    public CountriesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycle_view_cities, container, false);
        ArrayList<String> list = new ArrayList<>();
        list.add("RU");
        list.add("UA");
        CountryInListAdapter countryInListAdapter = new CountryInListAdapter(list, getContext());
        recyclerView.setAdapter(countryInListAdapter);
        int orientation = getActivity().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            recyclerView.setLayoutManager(linearLayoutManager);
        } else {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, Configuration.ORIENTATION_PORTRAIT);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
        }
        return recyclerView;
    }
}
