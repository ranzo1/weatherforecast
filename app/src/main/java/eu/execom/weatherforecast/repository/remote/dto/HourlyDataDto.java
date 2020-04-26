package eu.execom.weatherforecast.repository.remote.dto;

public class HourlyDataDto {

    private String summary;
    private String icon;
    private Long time;
    private float temperature;
    private float apparentTemperature;

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

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getApparentTemperature() {
        return apparentTemperature;
    }

    public void setApparentTemperature(float apparentTemperature) {
        this.apparentTemperature = apparentTemperature;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
