package domain.no.weatherapp.model;

import android.graphics.Bitmap;

/**
 * Created by Laurynas Mykolaitis on 2017.10.28.
 */

public class CityWeather {
    private String name;
    private double temp;
    private String conditions;
    private String icon;

    public CityWeather() { //Initialise all fields
        this.name = "";
        this.temp = 0.0;
        this.conditions = "";
        this.icon = "";
    }

    public CityWeather(String n, double t, String c, String i) { //Initialise all fields
        this.name = n;
        this.temp = t;
        this.conditions = c;
        this.icon = i;
    }

    //METHODS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) { this.temp = temp; }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Name: " + name + " T: " + temp + " C: " + conditions + " I: " + icon;
    }
}

