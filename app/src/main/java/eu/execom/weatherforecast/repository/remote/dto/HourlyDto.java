package eu.execom.weatherforecast.repository.remote.dto;

import java.util.List;

public class HourlyDto {

    private String summary;
    private String icon;

    private List<HourlyDataDto> data;

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

    public List<HourlyDataDto> getData() {
        return data;
    }

    public void setData(List<HourlyDataDto> data) {
        this.data = data;
    }
}
