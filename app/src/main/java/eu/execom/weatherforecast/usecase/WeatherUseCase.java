package eu.execom.weatherforecast.usecase;

import android.util.Log;

import eu.execom.weatherforecast.domain.Coordinates;
import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.system.LocationProvider;
import eu.execom.weatherforecast.usecase.dependency.repository.WeatherRemoteDao;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class WeatherUseCase {

    private WeatherRemoteDao weatherRemoteDao;
    private LocationProvider locationProvider;

    public WeatherUseCase(WeatherRemoteDao weatherRemoteDao,LocationProvider locationProvider) {
        this.weatherRemoteDao = weatherRemoteDao;
        this.locationProvider = locationProvider;
    }

    public Single<DailyWeather> getWeatherForecastForCurrentLocation() {
        return locationProvider.getCurrentLocation()
                .observeOn(Schedulers.io())
                .flatMap(coordinates -> weatherRemoteDao.getForecast(coordinates.getLatitude(), coordinates.getLongitude()))
                .subscribeOn(Schedulers.io());
    }
}
