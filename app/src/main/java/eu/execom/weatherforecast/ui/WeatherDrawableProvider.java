package eu.execom.weatherforecast.ui;

import org.androidannotations.annotations.EBean;

import eu.execom.weatherforecast.R;
import eu.execom.weatherforecast.domain.WeatherType;

@EBean
public class WeatherDrawableProvider {

    public int getWeatherIcons(WeatherType weatherType) {
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
            case HAIL:
                return R.drawable.ic_hail;
            case THUNDERSTORM:
                return R.drawable.ic_thunderstorm;
            case TORNADO:
                return R.drawable.ic_tornado;
        }
        return R.drawable.ic_error;
    }

    public int getWeatherBackground(WeatherType weatherType) {
        switch (weatherType) {
            case CLEAR_DAY:
                return R.drawable.clear_day_background;
            case CLEAR_NIGHT:
                return R.drawable.clear_night_background;
            case RAIN:
                return R.drawable.rain_background;
            case SNOW:
                return R.drawable.snow_background;
            case SLEET:
                return R.drawable.snow_background;
            case WIND:
                return R.drawable.wind_background;
            case FOG:
                return R.drawable.fog_background;
            case CLOUDY:
                return R.drawable.cloudy_background;
            case PARTLY_CLOUDY_DAY:
                return R.drawable.party_cloudy_day_background;
            case PARTLY_CLOUDY_NIGHT:
                return R.drawable.party_cloudy_night_background;
            case HAIL:
                return R.drawable.snow_background;
            case THUNDERSTORM:
                return R.drawable.thunderstorm_background;
            case TORNADO:
                return R.drawable.cloudy_background;
        }
        return R.drawable.ic_error;
    }
}