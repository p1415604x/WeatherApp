package domain.no.weatherapp.activity;

import java.util.List;

import domain.no.weatherapp.model.CityWeather;

/**
 * Created by Laurynas Mykolaitis on 2017.10.29.
 */

public interface MainActivityView {

    void displayCity(List<CityWeather> city);

    void displayNoCity();
}
