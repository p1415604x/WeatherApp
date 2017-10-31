package domain.no.weatherapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import domain.no.weatherapp.R;
import domain.no.weatherapp.model.CityWeather;

/**
 * Created by Laurynas Mykolaitis on 2017.10.29.
 */

public class CustomAdapter extends ArrayAdapter<CityWeather> {
    private Context mContext;
    private int mResource;

     public CustomAdapter(Context context, int resource, List<CityWeather> objects) {
        super(context, resource, objects);
         mContext = context;
         mResource = resource;
    }

    @Override @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        String name = getItem(position).getName();
        double temp = getItem(position).getTemp();
        String conditions = getItem(position).getConditions();
        String icon = getItem(position).getIcon();



        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvConditions = convertView.findViewById(R.id.tvConditions);
        ImageView ivIcon = convertView.findViewById(R.id.ivIcon);

        tvName.setText(name);
        tvConditions.setText("Current Temp: " + String.valueOf(temp) + " (°C)" + "\nConditions: " + conditions);
        switch(icon) {
            case "01d": {ivIcon.setImageResource(R.drawable.giedradiena);break;}
            case "01n": {ivIcon.setImageResource(R.drawable.giedranaktis);break;}
            case "02d": {ivIcon.setImageResource(R.drawable.debesuotasaule);break;}
            case "02n": {ivIcon.setImageResource(R.drawable.debesuotanaktis);break;}
            case "03d":
            case "03n": {ivIcon.setImageResource(R.drawable.debesuota);break;}
            case "04d":
            case "04n": {ivIcon.setImageResource(R.drawable.debesuota);break;}
            case "09d":
            case "09n": {ivIcon.setImageResource(R.drawable.lietussilpnas);break;}
            case "10d": {ivIcon.setImageResource(R.drawable.lietusdienastirprus);break;}
            case "10n": {ivIcon.setImageResource(R.drawable.lietusnaktistiprus);break;}
            case "11d":
            case "11n": {ivIcon.setImageResource(R.drawable.zaibas);break;}
            case "13d":
            case "13n": {ivIcon.setImageResource(R.drawable.sniegas);break;}
            case "50d":
            case "50n": {ivIcon.setImageResource(R.drawable.rukas);break;}
            default: {ivIcon.setImageResource(R.drawable.icon);}
        }



        return convertView;
    }


}