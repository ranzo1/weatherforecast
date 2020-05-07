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
                return R.drawable.ic_snow;
            case WIND:
                return R.drawable.ic_cloud_2;
            case FOG:
                return R.drawable.ic_fog_color;
            case CLOUDY:
                return R.drawable.ic_cloudy;
            case PARTLY_CLOUDY_DAY:
                return R.drawable.ic_partly_cloudy_day;
            case PARTLY_CLOUDY_NIGHT:
                return R.drawable.ic_partly_cloudy_night;
            case HAIL:
                return R.drawable.ic_hail_color;
            case THUNDERSTORM:
                return R.drawable.ic_thunderstorm;
            case TORNADO:
                return R.drawable.ic_tornado_color;
        }
        return R.drawable.ic_error;
    }

    public int getWeatherIconsMonoColor(WeatherType weatherType) {
        switch (weatherType) {
            case CLEAR_DAY:
                return R.drawable.ic_sun;
            case CLEAR_NIGHT:
                return R.drawable.ic_moon_6;
            case RAIN:
                return R.drawable.ic_rain_1;
            case SNOW:
                return R.drawable.ic_snowflake;
            case SLEET:
                return R.drawable.ic_snowflake;
            case WIND:
                return R.drawable.ic_wind;
            case FOG:
                return R.drawable.ic_fog;
            case CLOUDY:
                return R.drawable.ic_cloud;
            case PARTLY_CLOUDY_DAY:
                return R.drawable.ic_cloud_day;
            case PARTLY_CLOUDY_NIGHT:
                return R.drawable.ic_cloud_night;
            case HAIL:
                return R.drawable.ic_hail;
            case THUNDERSTORM:
                return R.drawable.ic_bolt;
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

    public int getTemperatureImage(int temperature) {
        if (temperature <= 0) {
            return R.drawable.ic_thermometer_low;
        }
        if (temperature >= 30) {
            return R.drawable.ic_thermometer_high;
        }
        return R.drawable.ic_thermometer_medium;
    }

    public int getPrecipProbabilityImage(int probability) {
        if (probability <= 20) {
            return R.drawable.ic_drop_0;
        }
        if (probability < 50) {
            return R.drawable.ic_drop_40;
        }
        if (probability <= 60) {
            return R.drawable.ic_drop_50;
        }
        if (probability < 100) {
            return R.drawable.ic_drop_80;
        }
        if (probability == 100) {
            return R.drawable.ic_drop_100;
        }

        return R.drawable.ic_error;
    }

    public int getMaxTemperatureImage(int temperature) {
        if (temperature <= 0) {
            return R.drawable.ic_temp_1;
        }
        if (temperature <= 10) {
            return R.drawable.ic_temp_2;
        }
        if (temperature <= 15) {
            return R.drawable.ic_temp_3;
        }
        if (temperature < 30) {
            return R.drawable.ic_temp_4;
        }
        return R.drawable.ic_temp_5;
    }
}