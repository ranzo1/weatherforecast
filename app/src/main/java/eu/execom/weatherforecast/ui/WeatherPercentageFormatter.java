package eu.execom.weatherforecast.ui;

import org.androidannotations.annotations.EBean;

@EBean
public class WeatherPercentageFormatter {
    public int getPercentage(float value) {
        return Math.round(value * 100);
    }
}
