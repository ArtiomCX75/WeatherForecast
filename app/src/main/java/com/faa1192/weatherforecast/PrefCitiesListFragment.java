package com.faa1192.weatherforecast;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrefCitiesListFragment extends CitiesListFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateList();
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                cu.moveToFirst();
                cu.move(position);
                City selectedCity =  new City(Integer.parseInt(cu.getString(0)), cu.getString(1));
                selectedCity.delFromDbPref(getActivity());
                updateList();
                return true;
            }
        });
    }

    void updateList(){
        cu = new PrefCursorCity().getCursor(getActivity().getApplicationContext());
        ca = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.custom_list1, cu , new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
        setListAdapter(ca);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        cu.moveToFirst();
        cu.move(position);
        City selectedCity =  new City(Integer.parseInt(cu.getString(0)), cu.getString(1));
        WeatherInfoFragment f = (WeatherInfoFragment) getFragmentManager().findFragmentById(R.id.fragment2);
        if(f!=null) {
            f.showWeather(selectedCity);
        }
        else{
            Intent intent = new Intent(getActivity(), WeatherInfoContainer.class);
            intent.putExtras(selectedCity.toBundle());
            startActivity(intent);
        }
    }
}
