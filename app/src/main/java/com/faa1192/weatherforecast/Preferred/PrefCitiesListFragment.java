package com.faa1192.weatherforecast.Preferred;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.faa1192.weatherforecast.R;

//Фрагмент со списком избранных городов
public class PrefCitiesListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycle_view, container, false);
        PrefCitiesAdapter prefCitiesAdapter = new PrefCitiesAdapter(PrefCityDBHelper.init(getContext()).getCityList(), getContext());
        recyclerView.setAdapter(prefCitiesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getActivity(), "mv: " + target + "=" + viewHolder, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //  Toast.makeText(getActivity(), "sw: "+direction+"="+viewHolder, Toast.LENGTH_LONG).show();
                PrefCitiesListFragment fragment = (PrefCitiesListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_pref);
                RecyclerView recyclerView = (RecyclerView) fragment.getView();
                PrefCitiesAdapter cwta = (PrefCitiesAdapter) recyclerView.getAdapter();
                PrefCityDBHelper.init(getActivity()).delFromDbPref(cwta.cityList.get(viewHolder.getAdapterPosition()));
                cwta = new PrefCitiesAdapter(PrefCityDBHelper.init(getActivity()).getCityList(), getActivity());
                recyclerView.setAdapter(cwta);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return recyclerView;
    }
}