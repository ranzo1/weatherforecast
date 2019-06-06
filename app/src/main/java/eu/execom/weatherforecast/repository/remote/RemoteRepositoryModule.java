package eu.execom.weatherforecast.repository.remote;

import android.content.Context;

import org.modelmapper.ModelMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.execom.weatherforecast.usecase.dependency.repository.WeatherRemoteDao;

@Module
public class RemoteRepositoryModule {

    private final Context context;

    public RemoteRepositoryModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    WeatherRemoteDao provideWeatherRemoteDao(HttpClient httpClient, ModelMapper modelMapper) {
        return new WeatherRemoteDaoImpl(httpClient, modelMapper);
    }

    @Provides
    @Singleton
    HttpClient providesHttpClient() {
        return new RetrofitClient(context);
    }

    @Provides
    ModelMapper providesModelMapper() {
        return new ModelMapper();
    }
}
