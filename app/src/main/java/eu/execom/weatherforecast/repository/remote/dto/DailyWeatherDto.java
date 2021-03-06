package eu.execom.weatherforecast.repository.remote.dto;

public class DailyWeatherDto {

    private String timezone;
    private CurrentlyDto currently;
    private DailyDto daily;
    private HourlyDto hourly;
    private MinutelyDto minutely;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public CurrentlyDto getCurrently() {
        return currently;
    }

    public void setCurrently(CurrentlyDto currently) {
        this.currently = currently;
    }

    public DailyDto getDaily() {
        return daily;
    }

    public void setDaily(DailyDto daily) {
        this.daily = daily;
    }

    public HourlyDto getHourly() {
        return hourly;
    }

    public void setHourly(HourlyDto hourly) {
        this.hourly = hourly;
    }

    public MinutelyDto getMinutely() {
        return minutely;
    }

    public void setMinutely(MinutelyDto minutely) {
        this.minutely = minutely;
    }
}