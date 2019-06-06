package eu.execom.weatherforecast.repository.remote.dto;

public class DailyDataDto {

    private int temperatureMinTime;
    private int temperatureMaxTime;
    private String summary;
    private String icon;
    private float temperatureMin;
    private float temperatureMax;
    private long time;

    public int getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public void setTemperatureMinTime(int temperatureMinTime) {
        this.temperatureMinTime = temperatureMinTime;
    }

    public int getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public void setTemperatureMaxTime(int temperatureMaxTime) {
        this.temperatureMaxTime = temperatureMaxTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
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

    public long getTime() {
        return time * 1000;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
