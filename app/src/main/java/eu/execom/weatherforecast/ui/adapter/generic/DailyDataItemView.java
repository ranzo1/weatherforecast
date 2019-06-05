package eu.execom.weatherforecast.ui.adapter.generic;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import eu.execom.weatherforecast.R;
import eu.execom.weatherforecast.domain.DailyData;

@EViewGroup(R.layout.daily_weather_item_view)
public class DailyDataItemView extends RelativeLayout {

    @ViewById
    TextView textViewDay;
    @ViewById
    ImageView imageViewWeather;

    LayoutParams layoutParams;


    public DailyDataItemView(Context context) {
        super(context);
        layoutParams =new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(151,15,15,15);
        setLayoutParams(layoutParams);

    }

    public void bind(DailyData dailyData) {
       setIcon(dailyData);
       setTime(dailyData);
    }

    private void setTime(DailyData data){
        textViewDay.setText(String.valueOf(data.getTime()).substring(0,3));
    }

    private void setIcon(DailyData data){
        if(data.getIcon().toString().equals("clear-day")){

            imageViewWeather.setImageResource(R.drawable.ic_clear_day);

        }else if(data.getIcon().toString().equals("clear-night")){

            imageViewWeather.setImageResource(R.drawable.ic_clear_night);

        }else if(data.getIcon().toString().equals("rain")){

            imageViewWeather.setImageResource(R.drawable.ic_rain);

        }else if(data.getIcon().toString().equals("snow")){

            imageViewWeather.setImageResource(R.drawable.ic_snow);

        }else if(data.getIcon().toString().equals("sleet")){

            imageViewWeather.setImageResource(R.drawable.ic_sleet);

        }else if(data.getIcon().toString().equals("wind")){

            imageViewWeather.setImageResource(R.drawable.ic_wind);

        }else if(data.getIcon().toString().equals("fog")){

            imageViewWeather.setImageResource(R.drawable.ic_fog);

        }else if(data.getIcon().toString().equals("cloudy")){

            imageViewWeather.setImageResource(R.drawable.ic_cloudy);

        }else if(data.getIcon().toString().equals("partly-cloudy-day")){

            imageViewWeather.setImageResource(R.drawable.ic_partly_cloudy_day);

        }else if(data.getIcon().toString().equals("partly-cloudy-night")){

            imageViewWeather.setImageResource(R.drawable.ic_partly_cloudy_night);

        }

    }


}
