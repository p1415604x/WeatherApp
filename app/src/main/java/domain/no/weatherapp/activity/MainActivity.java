package domain.no.weatherapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import domain.no.weatherapp.R;
import domain.no.weatherapp.adapter.CustomAdapter;
import domain.no.weatherapp.model.CityWeather;
import domain.no.weatherapp.presenter.WeatherActivityPresenter;
import domain.no.weatherapp.model.CityRepositoryImpl;

public class MainActivity extends AppCompatActivity implements MainActivityView {
    //Initialise
    private String TAG = MainActivity.class.getSimpleName();
    private CustomAdapter adapter;
    private ListView lv;
    private Button addCity;
    private int listPosition;
    private WeatherActivityPresenter presenter;

    //Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new WeatherActivityPresenter(this, new CityRepositoryImpl());

        lv =  findViewById(R.id.lvCities);  //ListView initialisation
        registerForContextMenu(lv);         //ListView add ContextMenu

        addCity = findViewById(R.id.baddCity); //Button initialisation
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogExecution(); //Pop out dialog window
            }
        });
    }
    //Methods

    @Override   //If city not empty
    public void displayCity(List<CityWeather> city) {
        adapter = new CustomAdapter(MainActivity.this, R.layout.custom_row, city);
        lv.setAdapter(adapter);
    }

    @Override   //If city empty
    public void displayNoCity() {
        adapter = new CustomAdapter(MainActivity.this, R.layout.custom_row, Arrays.asList(new CityWeather()));
        lv.setAdapter(adapter);
    }

    //Creates context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        listPosition = info.position;      // Get Index of long-clicked item
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Veiksmai:");
        menu.add(0, v.getId(), 1, "Pasalinti");
    }

    //Context menu selected item handler
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("Pasalinti")) {   // "Delete" chosen
            presenter.removeCity(listPosition);     // Remove item from list
            lv.setAdapter(adapter);                 // update info
        } else {
            return false;
        }
        return true;
    }

    //Create dialog window
    public void alertDialogExecution() {    //Dialog window to get users City name
        final EditText input = new EditText(this);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public String url;
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        url = "http://api.openweathermap.org/data/2.5/weather?q=" + input.getText().toString() + "&APPID=d48ae97aafc3fc0ada099bb10d1675ea";
                        presenter.loadCity(url);    //Execute Http call to Weather API
                        Log.e(TAG, "Loading City " + url);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(MainActivity.this, "Atsaukta", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        //Building alert dialog layout/structure
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        input.setHint("Stockholm, Vilnius, Moscow...");
        builder.setMessage("Are you sure?")
                .setNegativeButton("Atsaukti", dialogClickListener)
                .setPositiveButton("Prideti", dialogClickListener)
                .setTitle("Prideti Miesta")
                .setView(input)
                .setMessage("Iveskite miesto pavadinima.")
                .show();
    }

}
