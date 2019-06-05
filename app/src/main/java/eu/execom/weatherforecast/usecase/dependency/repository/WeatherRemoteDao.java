package eu.execom.weatherforecast.usecase.dependency.repository;

import eu.execom.weatherforecast.domain.DailyWeather;
import io.reactivex.Single;

public interface WeatherRemoteDao {

    Single<DailyWeather> getForecast(double lat, double lon);


}
