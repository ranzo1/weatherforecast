package eu.execom.weatherforecast.usecase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.execom.weatherforecast.usecase.dependency.repository.FavoriteCitiesDao;
import eu.execom.weatherforecast.usecase.dependency.repository.LocalCoordinatesDao;
import eu.execom.weatherforecast.usecase.dependency.repository.LocalWeatherDao;
import eu.execom.weatherforecast.usecase.dependency.repository.LocationProvider;
import eu.execom.weatherforecast.usecase.dependency.repository.SettingsDao;
import eu.execom.weatherforecast.usecase.dependency.repository.WeatherRemoteDao;

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    WeatherUseCase provideWeatherUseCase(WeatherRemoteDao weatherRemoteDao, LocationProvider locationProvider, LocalWeatherDao localWeatherDao, LocalCoordinatesDao localCoordinatesDao, FavoriteCitiesDao favoriteCitiesDao, SettingsDao settingsDao) {
        return new WeatherUseCase(weatherRemoteDao, locationProvider, localWeatherDao, localCoordinatesDao, favoriteCitiesDao, settingsDao);
    }
}
