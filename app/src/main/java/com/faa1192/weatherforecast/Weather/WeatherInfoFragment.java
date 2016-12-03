package com.faa1192.weatherforecast.Weather;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.faa1192.weatherforecast.Cities.City;
import com.faa1192.weatherforecast.R;
import com.faa1192.weatherforecast.databinding.CardWeatherInfoBinding;

//Фрагмент с данными о погоде конкретного города
public class WeatherInfoFragment extends Fragment {
    CardWeatherInfoBinding binding;

    public WeatherInfoFragment() {
        // Required empty public constructor
    }

    //Заполнение информации о погоде
    public void setInfo(City city) {
        binding = DataBindingUtil.setContentView(getActivity(), R.layout.card_weather_info);
        //     ((TextView) getActivity().findViewById(R.id.extra_city_name)).setText(getResources().getString(R.string.name_of_city) + city.name);
        binding.extraCityHumidity.setText(getResources().getString(R.string.humidity) + city.data.getHumidity());
        binding.extraCityPressure.setText(getResources().getString(R.string.pressure) + city.data.getPressure());
        binding.extraCityTemp.setText(getResources().getString(R.string.temperature) + city.data.getTemp());
        binding.extraCitySunrise.setText(getResources().getString(R.string.sunrise) + city.data.getSunrise());
        binding.extraCitySunset.setText(getResources().getString(R.string.sunset) + city.data.getSunset());
        binding.extraCityWindSpeed.setText(getResources().getString(R.string.wind_speed) + city.data.getWindSpeed());
        binding.extraCityWindDeg.setText(getResources().getString(R.string.wind_direction) + city.data.getWindDeg());
        binding.extraCityWeatherDescription.setText(getResources().getString(R.string.weather_description) + city.data.getWeatherDescription());
        //    ((TextView) getActivity().findViewById(R.id.extra_city_weather_main)).setText(getResources().getString(R.string.weather_main) + city.data.getWeatherMain());
        binding.extraCityTime.setText(getResources().getString(R.string.actual_time) + city.data.getTime());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_weather_info, container, false);
    }
}