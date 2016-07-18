package com.faa1192.weatherforecast;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    public void setInfo(City city){

        ((TextView) getActivity().findViewById(R.id.extra_city_name)).setText("name: "+city.data.getCityName());
        ((TextView) getActivity().findViewById(R.id.extra_city_humidity)).setText("humidity: "+city.data.getHumidity());
        ((TextView) getActivity().findViewById(R.id.extra_city_pressure)).setText("pressure: "+city.data.getPressure());
        ((TextView) getActivity().findViewById(R.id.extra_city_temp)).setText("temperature: "+city.data.getTemp());
        ((TextView) getActivity().findViewById(R.id.extra_city_sunrise)).setText("sunrise: "+city.data.getSunrise());
        ((TextView) getActivity().findViewById(R.id.extra_city_sunset)).setText("sunset: "+city.data.getSunset());
        ((TextView) getActivity().findViewById(R.id.extra_city_wind_speed)).setText("wind speed: "+city.data.getWindSpeed());
        ((TextView) getActivity().findViewById(R.id.extra_city_wind_deg)).setText("wind direction: "+city.data.getWindDeg());
        ((TextView) getActivity().findViewById(R.id.extra_city_weather_description)).setText("weather description: "+city.data.getWeatherDescription());
        ((TextView) getActivity().findViewById(R.id.extra_city_weather_main)).setText("weather main: "+city.data.getWeatherMain());
        ((TextView) getActivity().findViewById(R.id.extra_city_time)).setText("time: "+city.data.getTime());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_weather_info, container, false);
 }

}
