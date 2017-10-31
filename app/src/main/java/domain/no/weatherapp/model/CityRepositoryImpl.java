package domain.no.weatherapp.model;


import java.util.ArrayList;
import java.util.List;

public class CityRepositoryImpl implements CityRepository {
    //Initialise
    private List<CityWeather> list;

    //Constructor
    public CityRepositoryImpl() {
        list = new ArrayList<>();
    }

    //Methods
    @Override
    public List<CityWeather> getCities() {
        return list;
    }

    @Override
    public void addCity(CityWeather cw) {
        if(cw!=null)
        list.add(cw);
    }

    @Override
    public void removeCity(int i) {
        list.remove(i);
    }


}
