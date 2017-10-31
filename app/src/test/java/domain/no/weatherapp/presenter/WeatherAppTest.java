package domain.no.weatherapp.presenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import domain.no.weatherapp.activity.MainActivityView;
import domain.no.weatherapp.model.CityRepositoryImpl;
import domain.no.weatherapp.model.CityWeather;

import static org.junit.Assert.assertEquals;

/**
 * Created by Laurynas Mykolaitis on 2017.10.31.
 */

public class WeatherAppTest {
    CityWeather a = Mockito.mock(CityWeather.class);
    CityWeather b = Mockito.mock(CityWeather.class);
    CityRepositoryImpl model = new CityRepositoryImpl();
    MainActivityView view = Mockito.mock(MainActivityView.class);
    WeatherActivityPresenter presenter = new WeatherActivityPresenter(view, model);

    @Test
    public void modelGetCitiesFromEmptyModelTest() {
        List<CityWeather> list = model.getCities();
        assertEquals(Collections.<CityWeather>emptyList(), list);
    }

    @Test
    public void modelAddTest() {
        model.addCity(a); model.addCity(b);
        assertEquals(2, model.getCities().size());
    }

    @Test
    public void modelAddNullTest() {
        model.addCity(null); model.addCity(null);
        assertEquals(0, model.getCities().size());
    }

    @Test
    public void modelRemoveTest() {
        model.addCity(a); model.addCity(b);
        model.removeCity(0);
        assertEquals(1, model.getCities().size());
    }

    @Test
    public void presenterRemoveCityListNotEmptyTest() {
        model.addCity(a); model.addCity(b);
        presenter.removeCity(0);
        assertEquals(1, model.getCities().size());
    }

    @Test
    public void presenterRemoveCityListEmptyTest() {
        presenter.removeCity(0);
        assertEquals(0, model.getCities().size());
    }

}
