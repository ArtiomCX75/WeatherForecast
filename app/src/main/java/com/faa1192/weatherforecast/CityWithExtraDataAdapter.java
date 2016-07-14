package com.faa1192.weatherforecast;

import java.util.List;

/**
 * Created by faa11 on 14.07.2016.
 */

public class CityWithExtraDataAdapter extends CityWithTempAdapter {

    public CityWithExtraDataAdapter(List<String> cities, List<WeatherData> wd) {
        super(cities, wd);
    }
}
