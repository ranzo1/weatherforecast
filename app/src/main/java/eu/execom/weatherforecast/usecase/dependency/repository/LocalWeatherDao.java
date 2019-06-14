package eu.execom.weatherforecast.usecase.dependency.repository;

import eu.execom.weatherforecast.domain.DailyWeather;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface LocalWeatherDao {

    Completable saveDailyWeather(DailyWeather dailyWeather, String city);
    Completable saveDailyWeatherForCurrentLocation(DailyWeather dailyWeather);
    Single<DailyWeather> getForecast(String city);
    Single<DailyWeather> getForecastForCurrentLocation();
}
