package eu.execom.weatherforecast.repository.remote.dto;

public class DailyWeatherDto {

  String timezone;
  CurrentlyDto currently;
  DailyDto daily;

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
}
