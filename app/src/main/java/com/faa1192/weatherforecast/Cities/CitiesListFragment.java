package com.faa1192.weatherforecast.Cities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faa1192.weatherforecast.Countries.CountriesActivity;
import com.faa1192.weatherforecast.R;

//Фрагмент со списком всех городов
public class CitiesListFragment extends Fragment {
    public CitiesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycle_view, container, false);
        CityInListAdapter cityInListAdapter = new CityInListAdapter(CityDBHelper.init(getActivity()).getCityList(""), getContext());
        int cityCount = cityInListAdapter.getItemCount();
        if(cityCount==0){
            getActivity().startActivityForResult(new Intent(getActivity(), CountriesActivity.class), 1);
            getActivity().overridePendingTransition(R.anim.alpha_on, R.anim.alpha_off);
        }
        recyclerView.setAdapter(cityInListAdapter);
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