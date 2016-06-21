package com.faa1192.weatherforecast;

import java.util.ArrayList;

/**
 * Created by faa11 on 21.06.2016.
 */

public class City {
    public String name = "";
    public int id = -1;
    public static ArrayList<City> citiesList = new ArrayList<>();
    static{
        citiesList.add(new City("Kazan", 551487));
        citiesList.add(new City("Moscow", 524901));
    }
    public City(){

    }

    public City(String name, int id){
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }


}
