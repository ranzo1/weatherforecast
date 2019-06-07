package eu.execom.weatherforecast.ui;

import org.androidannotations.annotations.EBean;

import eu.execom.weatherforecast.R;
import eu.execom.weatherforecast.domain.WeatherType;

@EBean
public class WeatherIconProvider {

    public int getWeatherIcon(WeatherType weatherType) {
        switch (weatherType) {
            case CLEAR_DAY:
                return R.drawable.ic_clear_day;
            case CLEAR_NIGHT:
                return R.drawable.ic_clear_night;
            case RAIN:
                return R.drawable.ic_rain;
            case SNOW:
                return R.drawable.ic_snow;
            case SLEET:
                return R.drawable.ic_sleet;
            case WIND:
                return R.drawable.ic_wind;
            case FOG:
                return R.drawable.ic_fog;
            case CLOUDY:
                return R.drawable.ic_cloudy;
            case PARTLY_CLOUDY_DAY:
                return R.drawable.ic_partly_cloudy_day;
            case PARTLY_CLOUDY_NIGHT:
                return R.drawable.ic_partly_cloudy_night;
        }
        return R.drawable.ic_error;
    }
}
