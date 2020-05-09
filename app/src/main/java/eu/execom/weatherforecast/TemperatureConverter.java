package eu.execom.weatherforecast;

import org.androidannotations.annotations.EBean;

@EBean
public class TemperatureConverter {

    public int convertToCelsius(float temperature) {
        float value = ((temperature - 32) * 5) / 9;
        return Math.round(value);
    }

    public int convertToFahrenheit(float temperature) {
        float value = temperature * (9f / 5) + 32;
        return Math.round(value);
    }
}