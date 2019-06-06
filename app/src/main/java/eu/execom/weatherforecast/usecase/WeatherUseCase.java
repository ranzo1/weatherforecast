package eu.execom.weatherforecast.usecase;

import eu.execom.weatherforecast.domain.Coordinates;
import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.usecase.dependency.repository.WeatherRemoteDao;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class WeatherUseCase {

    private WeatherRemoteDao weatherRemoteDao;

    public WeatherUseCase(WeatherRemoteDao weatherRemoteDao) {

        this.weatherRemoteDao = weatherRemoteDao;
    }

    public Single<DailyWeather> getWeatherForecastForCurrentLocation(Coordinates coordinates) {

        return weatherRemoteDao.getForecast(coordinates.getLatitude(), coordinates.getLongitude()).subscribeOn(Schedulers.io());
    }
}
