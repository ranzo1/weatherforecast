package eu.execom.weatherforecast.usecase;


import eu.execom.weatherforecast.domain.Coordinates;
import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.domain.LocationData;
import eu.execom.weatherforecast.usecase.dependency.repository.LocationProvider;
import eu.execom.weatherforecast.usecase.dependency.repository.WeatherRemoteDao;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.BiFunction;
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
                .flatMap((Function<Coordinates, SingleSource<DailyWeather>>) coordinates -> weatherRemoteDao.getForecast(coordinates.getLatitude(), coordinates.getLongitude())
                        .zipWith(locationProvider.getNameOfLocation(coordinates), (dailyWeather, cityName) -> {
                            LocationData locationData = new LocationData(cityName, coordinates);
                            dailyWeather.setLocationData(locationData);
                            return dailyWeather;
                        }))
                .subscribeOn(Schedulers.io());
    }

    public Single<DailyWeather> getWeatherForecast(String cityName){
        return locationProvider.getLocationByCityName(cityName).observeOn(Schedulers.io())
                .flatMap(coordinates -> weatherRemoteDao.getForecast(coordinates.getLatitude(), coordinates.getLongitude())
                        .map(dailyWeather -> {
                            LocationData locationData = new LocationData(cityName, coordinates);
                            dailyWeather.setLocationData(locationData);
                            return dailyWeather;
                        }))
                .subscribeOn(Schedulers.io());
    }
}
