package com.faa1192.weatherforecast;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CitiesListFragment extends ListFragment {
    Cursor cu;

    public CitiesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter la = new ArrayAdapter<City>(getActivity(), R.layout.custom_list1, City.citiesList);
        cu = new CursorCity().getCursor(getActivity().getApplicationContext());
        CursorAdapter ca = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.custom_list1, cu , new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);

        ListView lv = getListView();
       // lv.setAdapter(ca);
        setListAdapter(ca);




    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        cu.moveToFirst();
        cu.move(position);
        City selectedCity =  new City(cu.getString(1), Integer.parseInt(cu.getString(0)));
        Log.e("my", selectedCity.toString()+"  "+cu.getString(0)+"   "+"pos:"+position);
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
