package eu.execom.weatherforecast.ui;

import android.content.Context;
import android.content.res.Resources;

import org.androidannotations.annotations.EBean;

import eu.execom.weatherforecast.R;

@EBean
public class DescriptionWeatherProvider {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public String getWindType(float windSpeed) {
        if (windSpeed < 1) {
            return context.getString(R.string.calm);
        }
        if (windSpeed <= 3) {
            return context.getString(R.string.light_air);
        }
        if (windSpeed <= 6) {
            return context.getString(R.string.light_breeze);
        }
        if (windSpeed <= 10) {
            return context.getString(R.string.gentle_breeze);
        }
        if (windSpeed <= 16) {
            return context.getString(R.string.moderate_breeze);
        }
        if (windSpeed <= 21) {
            return context.getString(R.string.fresh_breeze);
        }
        if (windSpeed <= 27) {
            return context.getString(R.string.strong_breeze);
        }
        if (windSpeed <= 33) {
            return context.getString(R.string.near_gale);
        }
        if (windSpeed <= 40) {
            return context.getString(R.string.gale);
        }
        if (windSpeed <= 47) {
            return context.getString(R.string.strong_gale);
        }
        if (windSpeed <= 55) {
            return context.getString(R.string.storm);
        }
        if (windSpeed <= 63) {
            return context.getString(R.string.violent_storm);
        }
        return context.getString(R.string.hurricane);
    }
}
