package eu.execom.weatherforecast.repository.remote.dto;

public class MinutelyDto {

    private String summary;
    private String icon;

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
}
