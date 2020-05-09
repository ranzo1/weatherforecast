package eu.execom.weatherforecast.domain;

public class Minutely {

    private String summary;
    private WeatherType icon;

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
}
