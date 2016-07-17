package com.faa1192.weatherforecast;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrefCitiesListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.recycle_view_cities, container, false);
        CityWithTempAdapter cwta = new CityWithTempAdapter(new PrefCursorCity().getCityList(getContext()), getContext());
        rv.setAdapter(cwta);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(linearLayoutManager);
        return rv;
    }

  /*  void updateList(){
        cu = new PrefCursorCity().getCursor(getActivity().getApplicationContext());
        ca = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.custom_list1, cu , new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
        setListAdapter(ca);
    }*/

 /*   @Override
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
    }*/
}
