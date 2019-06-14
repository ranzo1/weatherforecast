package eu.execom.weatherforecast.repository.remote.dto;

import java.util.List;

public class DailyDto {

    private String summary;
    private String icon;

    private List<DailyDataDto> data;

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

    public List<DailyDataDto> getData() {
        return data;
    }

    public void setData(List<DailyDataDto> data) {
        this.data = data;
    }
}