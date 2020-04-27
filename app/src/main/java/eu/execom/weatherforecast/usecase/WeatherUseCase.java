package eu.execom.weatherforecast.usecase;


import java.util.Date;

import eu.execom.weatherforecast.domain.Coordinates;
import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.domain.Hourly;
import eu.execom.weatherforecast.domain.LocationData;
import eu.execom.weatherforecast.usecase.dependency.repository.LocalCoordinatesDao;
import eu.execom.weatherforecast.usecase.dependency.repository.LocalWeatherDao;
import eu.execom.weatherforecast.usecase.dependency.repository.LocationProvider;
import eu.execom.weatherforecast.usecase.dependency.repository.WeatherRemoteDao;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class WeatherUseCase {

    private WeatherRemoteDao weatherRemoteDao;
    private LocationProvider locationProvider;
    private LocalWeatherDao localWeatherDao;
    private LocalCoordinatesDao localCoordinatesDao;


    public WeatherUseCase(WeatherRemoteDao weatherRemoteDao, LocationProvider locationProvider, LocalWeatherDao localWeatherDao, LocalCoordinatesDao localCoordinatesDao) {
        this.weatherRemoteDao = weatherRemoteDao;
        this.locationProvider = locationProvider;
        this.localWeatherDao = localWeatherDao;
        this.localCoordinatesDao = localCoordinatesDao;
    }

    public Single<DailyWeather> getWeatherForecastForCurrentLocation() {
        return locationProvider.getCurrentLocation()
                .observeOn(Schedulers.io())
                .flatMap((Function<Coordinates, SingleSource<DailyWeather>>) coordinates -> weatherRemoteDao.getForecast(coordinates.getLatitude(), coordinates.getLongitude())
                        .zipWith(locationProvider.getNameOfLocation(coordinates), (dailyWeather, cityName) -> {
                            LocationData locationData = new LocationData(cityName, coordinates);
                            dailyWeather.setLocationData(locationData);
                            Date currentDate = new Date();
                            dailyWeather.setLastTimeSync(currentDate);
                            return dailyWeather;
                        }))
                .doOnSuccess(dailyWeather -> localWeatherDao.saveDailyWeatherForCurrentLocation(dailyWeather).subscribe())
                .onErrorReturn(throwable -> localWeatherDao.getForecastForCurrentLocation().blockingGet())
                .subscribeOn(Schedulers.io());
    }

    public Single<DailyWeather> getWeatherForecast(String cityName) {
        return locationProvider.getLocationByCityName(cityName).observeOn(Schedulers.io())
                .doOnSuccess(coordinates -> localCoordinatesDao.saveCoordinates(coordinates, cityName).subscribe()).onErrorReturn(throwable -> localCoordinatesDao.getCoordinates(cityName).blockingGet())
                .flatMap(coordinates -> weatherRemoteDao.getForecast(coordinates.getLatitude(), coordinates.getLongitude())
                        .map(dailyWeather -> {
                            LocationData locationData = new LocationData(cityName, coordinates);
                            dailyWeather.setLocationData(locationData);
                            Date currentDate = new Date();
                            dailyWeather.setLastTimeSync(currentDate);
                            return dailyWeather;
                        })
                        .doOnSuccess(dailyWeather -> localWeatherDao.saveDailyWeather(dailyWeather, cityName).subscribe())
                        .onErrorReturn(throwable -> localWeatherDao.getForecast(cityName).blockingGet())
                        .subscribeOn(Schedulers.io()));
    }
}