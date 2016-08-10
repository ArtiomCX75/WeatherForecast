package com.faa1192.weatherforecast.Cities;

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

public class CitiesListFragment extends Fragment {
    public CitiesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.recycle_view_cities, container, false);
        CityInListAdapter cila = new CityInListAdapter(CityDBHelper.init(getActivity()).getCityList(""), getContext());
        rv.setAdapter(cila);
        int orientation = getActivity().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            rv.setLayoutManager(linearLayoutManager);
        } else {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, Configuration.ORIENTATION_PORTRAIT);
            rv.setLayoutManager(staggeredGridLayoutManager);
        }
        return rv;
    }
    public View set(String s){
        RecyclerView rv = (RecyclerView) getView().findViewById(R.id.recycle_view_cities);
        CityInListAdapter cwa = new CityInListAdapter(CityDBHelper.init(getActivity()).getCityList(s), getContext());
        rv.setAdapter(cwa);
        int orientation = getActivity().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            rv.setLayoutManager(linearLayoutManager);
        } else {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, Configuration.ORIENTATION_PORTRAIT);
            rv.setLayoutManager(staggeredGridLayoutManager);
        }
        return rv;
    }
}