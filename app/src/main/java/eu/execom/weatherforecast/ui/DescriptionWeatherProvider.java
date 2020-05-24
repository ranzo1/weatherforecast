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

    public String getWindDescription(String wind) {
        if (wind.equals(context.getString(R.string.calm))) {
            return "Calm, smoke rises vertically.";
        }
        if (wind.equals(context.getString(R.string.light_air))) {
            return "Smoke drift indicates wind direction; vanes do not move.";
        }
        if (wind.equals(context.getString(R.string.light_breeze))) {
            return "Wind felt on face, leaves rustle, vanes begin to move.";
        }
        if (wind.equals(context.getString(R.string.gentle_breeze))) {
            return "Leaves, small twigs in constant motion, light flags extended.";
        }
        if (wind.equals(context.getString(R.string.moderate_breeze))) {
            return "Dust, leaves and loose paper raised up, small branches move.";
        }
        if (wind.equals(context.getString(R.string.fresh_breeze))) {
            return "Small trees begin tu sway.";
        }
        if (wind.equals(context.getString(R.string.strong_breeze))) {
            return "Large branches of trees in motion, whistling heard in wires.";
        }
        if (wind.equals(context.getString(R.string.near_gale))) {
            return "Whole trees in motion, resistance felt in walking against the wind";
        }
        if (wind.equals(context.getString(R.string.gale))) {
            return "Twigs and small branches broken off trees";
        }
        if (wind.equals(context.getString(R.string.gale))) {
            return "Slight structural damage occurs, slate blown from roofs.";
        }
        if (wind.equals(context.getString(R.string.storm))) {
            return "Seldom experienced on land; trees broken; structural damage occurs";
        }
        if (wind.equals(context.getString(R.string.violent_storm))) {
            return "Very rarely experienced on land, usually with widespread damage.";
        }
        if (wind.equals(context.getString(R.string.light_air))) {
            return "Violence and destruction.";
        }
        return context.getString(R.string.error_message);
    }
}
