package eu.execom.weatherforecast;

import org.androidannotations.annotations.EBean;

import eu.execom.weatherforecast.domain.Currently;

@EBean
public class ConverterTemperature {

    public int convertToCelsius(float temperature) {

        float value = ((temperature - 32) * 5) / 9;
        return Math.round(value);
    }
}
