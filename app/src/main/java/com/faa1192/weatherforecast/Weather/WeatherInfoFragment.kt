package com.faa1192.weatherforecast.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.faa1192.weatherforecast.R
import com.faa1192.weatherforecast.cities.City
import com.faa1192.weatherforecast.databinding.CardWeatherInfoBinding

//Фрагмент с данными о погоде конкретного города
class WeatherInfoFragment : Fragment() {
    var binding: CardWeatherInfoBinding? = null

    //Заполнение информации о погоде
    fun setInfo(city: City) {
        binding = DataBindingUtil.setContentView(
            requireActivity(),
            R.layout.card_weather_info
        ) as CardWeatherInfoBinding
        //     ((TextView) getActivity().findViewById(R.id.extra_city_name)).setText(getResources().getString(R.string.name_of_city) + city.name);
        binding!!.extraCityHumidity.text =
            resources.getString(R.string.humidity) + city.data.humidity
        binding!!.extraCityPressure.text =
            resources.getString(R.string.pressure) + city.data.pressure
        binding!!.extraCityTemp.text = resources.getString(R.string.temperature) + city.data.temp
        binding!!.extraCitySunrise.text =
            resources.getString(R.string.sunrise) + city.data.sunrise
        binding!!.extraCitySunset.text = resources.getString(R.string.sunset) + city.data.sunset
        binding!!.extraCityWindSpeed.text =
            resources.getString(R.string.wind_speed) + city.data.windSpeed
        binding!!.extraCityWindDeg.text =
            resources.getString(R.string.wind_direction) + city.data.windDeg
        binding!!.extraCityWeatherDescription.text =
            resources.getString(R.string.weather_description) + city.data.weatherDescription
        //    ((TextView) getActivity().findViewById(R.id.extra_city_weather_main)).setText(getResources().getString(R.string.weather_main) + city.data.getWeatherMain());
        binding!!.extraCityTime.text =
            resources.getString(R.string.actual_time) + city.data.getTime()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.card_weather_info, container, false)
    }
}