package com.faa1192.weatherforecast.Preferred;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faa1192.weatherforecast.R;

//Фрагмент со списком избранных городов
public class PrefCitiesListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycle_view_cities, container, false);
        CityWithTempAdapter cityWithTempAdapter = new CityWithTempAdapter(PrefCityDBHelper.init(getContext()).getCityList(), getContext());
        recyclerView.setAdapter(cityWithTempAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        return recyclerView;
    }
}