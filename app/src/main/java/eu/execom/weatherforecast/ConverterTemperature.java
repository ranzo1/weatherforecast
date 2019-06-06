package eu.execom.weatherforecast;

import org.androidannotations.annotations.EBean;

import eu.execom.weatherforecast.domain.Currently;

@EBean
public class ConverterTemperature {

    public int convertToCelsius(Currently currently) {

        float value = ((currently.getTemperature() - 32) * 5) / 9;
        return Math.round(value);
    }
}
