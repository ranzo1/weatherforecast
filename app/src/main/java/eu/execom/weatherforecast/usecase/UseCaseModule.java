package eu.execom.weatherforecast.usecase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.execom.weatherforecast.usecase.dependency.repository.WeatherRemoteDao;

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    WeatherUseCase provideWeatherUseCase(WeatherRemoteDao weatherRemoteDao) {
        return new WeatherUseCase(weatherRemoteDao);
    }
}
