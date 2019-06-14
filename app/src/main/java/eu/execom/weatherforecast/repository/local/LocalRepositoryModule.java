package eu.execom.weatherforecast.repository.local;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.execom.weatherforecast.repository.local.shared_preferences.LocalCoordinatesDaoImpl;
import eu.execom.weatherforecast.repository.local.shared_preferences.LocalWeatherDaoImpl;
import eu.execom.weatherforecast.usecase.dependency.repository.LocalCoordinatesDao;
import eu.execom.weatherforecast.usecase.dependency.repository.LocalWeatherDao;

@Module
public class LocalRepositoryModule {

    private final Context context;

    public LocalRepositoryModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    LocalWeatherDao provideLocalWeatherDao() {
        return new LocalWeatherDaoImpl(context);
    }

    @Provides
    @Singleton
    LocalCoordinatesDao provideLocalCoordinatesDao() {
        return new LocalCoordinatesDaoImpl(context);
    }
}