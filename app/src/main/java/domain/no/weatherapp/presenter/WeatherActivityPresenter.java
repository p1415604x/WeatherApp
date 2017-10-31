package domain.no.weatherapp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import domain.no.weatherapp.Handler.HttpHandler;
import domain.no.weatherapp.activity.MainActivityView;
import domain.no.weatherapp.model.CityWeather;
import domain.no.weatherapp.model.CityRepository;

/**
 * Created by Laurynas Mykolaitis on 2017.10.29.
 */

public class WeatherActivityPresenter {
    //Initialise
    private String TAG = WeatherActivityPresenter.class.getSimpleName();
    private MainActivityView view;
    private CityRepository model;
    private String url;

    //Constructor
    public WeatherActivityPresenter(MainActivityView view, CityRepository model) {
        this.view = view;
        this.model = model;
    }

    //Methods
    public void removeCity(int i) { //Remove city from model
        if(!(model.getCities().size() <= 0)) {
            model.removeCity(i);
        }
    }

    public void loadCity(String url) {
        this.url=url;
        new getCityRequest().execute(); //Execute Http call to Weather API
    }

    //Class DOWNLOAD JSON AND STORE IN MODEL
    class getCityRequest extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null)
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr); //Create JSONobject
                    CityWeather city = new CityWeather();  // Creating single city
                    //Adding values:
                    //City name
                    city.setName(jsonObj.getString("name"));
                    //City weather conditions
                    city.setConditions(jsonObj.getJSONArray("weather")
                            .getJSONObject(0)
                            .getString("main")
                            + ": " +
                            jsonObj.getJSONArray("weather")
                                    .getJSONObject(0)
                                    .getString("description"));
                    //City temperature translation From Kelvin to Celsium + Formatting result
                    DecimalFormat df = new DecimalFormat("#.##");
                    city.setTemp(Double.parseDouble(df.format(jsonObj.getJSONObject("main")
                            .getDouble("temp") - 273.15)));
                    //City icon code
                    city.setIcon(jsonObj.getJSONArray("weather")
                            .getJSONObject(0)
                            .getString("icon"));
                   model.addCity(city);   // adding city to model
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    ((Activity) view).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(((Context) view).getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                Log.e(TAG, "Couldn't get json from server.");
                ((Activity) view).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(((Context) view).getApplicationContext(),
                                "Couldn't get json from server.",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Log.e(TAG, "onPostExecute");
            if(model.getCities().isEmpty()) {
                view.displayNoCity();
            } else {
                view.displayCity(model.getCities());
            }
        }
    }


}
