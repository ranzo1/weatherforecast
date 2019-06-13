package eu.execom.weatherforecast.domain;

import java.io.Serializable;
import java.util.Date;

public class DailyData implements Serializable {

    private Date temperatureMinTime;
    private Date temperatureMaxTime;
    private String summary;
    private WeatherType icon;
    private float temperatureMin;
    private float temperatureMax;
    private float humidity;
    private int uvIndex;
    private Date time;
    private Date sunriseTime;
    private Date sunsetTime;

    public Date getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(Date sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public Date getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(Date sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public Date getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public void setTemperatureMinTime(Date temperatureMinTime) {
        this.temperatureMinTime = temperatureMinTime;
    }

    public Date getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public void setTemperatureMaxTime(Date temperatureMaxTime) {
        this.temperatureMaxTime = temperatureMaxTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public WeatherType getIcon() {
        return icon;
    }

    public void setIcon(WeatherType icon) {
        this.icon = icon;
    }

    public float getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(float temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public float getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(float temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public int getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(int uvIndex) {
        this.uvIndex = uvIndex;
    }
}
