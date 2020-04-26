package eu.execom.weatherforecast.domain;

import java.util.List;

public class Hourly {

    private String summary;
    private String icon;

    private List<HourlyData> data;

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

    public List<HourlyData> getData() {
        return data;
    }

    public void setData(List<HourlyData> data) {
        this.data = data;
    }
}
