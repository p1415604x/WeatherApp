package domain.no.weatherapp.model;

import java.util.List;

import domain.no.weatherapp.model.CityWeather;

/**
 * Created by Laurynas Mykolaitis on 2017.10.29.
 */

public interface CityRepository {

    List<CityWeather> getCities();

    void addCity(CityWeather cw);

    void removeCity(int i);

}
