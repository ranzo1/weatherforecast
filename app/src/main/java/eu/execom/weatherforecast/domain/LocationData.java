package eu.execom.weatherforecast.domain;

import java.io.Serializable;

public class LocationData implements Serializable {
    private String cityName;
    private Coordinates coordinates;

    public LocationData(String cityName, Coordinates coordinates) {
        this.cityName = cityName;
        this.coordinates = coordinates;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
