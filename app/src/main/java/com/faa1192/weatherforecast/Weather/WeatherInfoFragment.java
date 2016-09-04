package com.faa1192.weatherforecast.Weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faa1192.weatherforecast.Cities.City;
import com.faa1192.weatherforecast.R;

//Фрагмент с данными о погоде конкретного города
public class WeatherInfoFragment extends Fragment {

    public WeatherInfoFragment() {
        // Required empty public constructor
    }

    //Заполнение информации о погоде
    public void setInfo(City city) {
        ((TextView) getActivity().findViewById(R.id.extra_city_name)).setText(getResources().getString(R.string.name_of_city) + city.data.getCityName());
        ((TextView) getActivity().findViewById(R.id.extra_city_humidity)).setText(getResources().getString(R.string.humidity) + city.data.getHumidity());
        ((TextView) getActivity().findViewById(R.id.extra_city_pressure)).setText(getResources().getString(R.string.pressure) + city.data.getPressure());
        ((TextView) getActivity().findViewById(R.id.extra_city_temp)).setText(getResources().getString(R.string.temperature) + city.data.getTemp());
        ((TextView) getActivity().findViewById(R.id.extra_city_sunrise)).setText(getResources().getString(R.string.sunrise) + city.data.getSunrise());
        ((TextView) getActivity().findViewById(R.id.extra_city_sunset)).setText(getResources().getString(R.string.sunset) + city.data.getSunset());
        ((TextView) getActivity().findViewById(R.id.extra_city_wind_speed)).setText(getResources().getString(R.string.wind_speed) + city.data.getWindSpeed());
        ((TextView) getActivity().findViewById(R.id.extra_city_wind_deg)).setText(getResources().getString(R.string.wind_direction) + city.data.getWindDeg());
        ((TextView) getActivity().findViewById(R.id.extra_city_weather_description)).setText(getResources().getString(R.string.weather_description) + city.data.getWeatherDescription());
        ((TextView) getActivity().findViewById(R.id.extra_city_weather_main)).setText(getResources().getString(R.string.weather_main) + city.data.getWeatherMain());
        ((TextView) getActivity().findViewById(R.id.extra_city_time)).setText(getResources().getString(R.string.actual_time) + city.data.getTime());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_weather_info, container, false);
    }
}