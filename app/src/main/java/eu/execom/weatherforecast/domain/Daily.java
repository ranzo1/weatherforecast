package eu.execom.weatherforecast.domain;

import java.io.Serializable;
import java.util.List;

public class Daily implements Serializable {

    private String summary;
    private WeatherType icon;
    private List<DailyData> data;

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

    public List<DailyData> getData() {
        return data;
    }

    public void setData(List<DailyData> data) {
        this.data = data;
    }
}
