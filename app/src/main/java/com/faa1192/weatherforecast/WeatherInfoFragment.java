package com.faa1192.weatherforecast;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherInfoFragment extends Fragment {


    public WeatherInfoFragment() {
        // Required empty public constructor
    }

    private  void setInfo(String s){

        ((TextView) getActivity().findViewById(R.id.info)).setText(s);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.recycle_view_cities, container, false);
        CityWithExtraDataAdapter cwta = new CityWithExtraDataAdapter(new PrefCursorCity().getCityList(getContext()), getContext());
        rv.setAdapter(cwta);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);
        return rv;
    }

}
