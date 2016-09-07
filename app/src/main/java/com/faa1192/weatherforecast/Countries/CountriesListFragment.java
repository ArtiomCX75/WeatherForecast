package com.faa1192.weatherforecast.Countries;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faa1192.weatherforecast.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//Фрагмент со списком стран
public class CountriesListFragment extends Fragment {
    ArrayList<String> list = new ArrayList<>();
    //   https://raw.githubusercontent.com/ArtiomCX75/WeatherForecast/move_citieslist_to_server/citieslist/list.txt

    public CountriesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycle_view, container, false);
        update();
        //  list.add("RU");
        //   list.add("UA");
        return recyclerView;
    }

    public void update() {
        DownloadList dl = new DownloadList();
        dl.execute();
        getResources().getString(R.string.RU);
    }

    class DownloadList extends AsyncTask {
        boolean success = false;

        @Override
        protected Object doInBackground(Object[] objects) {
            success = false;
            list.clear();
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().get().url("https://raw.githubusercontent.com/ArtiomCX75/WeatherForecast/develop/citieslist/list.txt").build();
                Response response = okHttpClient.newCall(request).execute();
                BufferedReader br = new BufferedReader(response.body().charStream());
                String temp = br.readLine();
                while (temp != null && !temp.isEmpty()) {
                    list.add(temp);
                    temp = br.readLine();
                }
                success = true;
            } catch (IOException e) {
                success = false;
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (success) {
                CountryInListAdapter countryInListAdapter = new CountryInListAdapter(list, getContext());
                CountriesListFragment clf = (CountriesListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_coun);
                RecyclerView recyclerView = (RecyclerView) clf.getView();

                int orientation = getActivity().getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setSmoothScrollbarEnabled(true);
                    recyclerView.setLayoutManager(linearLayoutManager);
                } else {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, Configuration.ORIENTATION_PORTRAIT);
                    recyclerView.setLayoutManager(staggeredGridLayoutManager);
                }
                recyclerView.setAdapter(countryInListAdapter);
            }
        }
    }

}