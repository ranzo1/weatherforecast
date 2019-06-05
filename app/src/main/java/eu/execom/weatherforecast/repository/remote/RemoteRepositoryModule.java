package eu.execom.weatherforecast.repository.remote;

import org.modelmapper.ModelMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.execom.weatherforecast.usecase.dependency.repository.WeatherRemoteDao;

@Module
public class RemoteRepositoryModule {

    @Provides
    @Singleton
    WeatherRemoteDao provideWeatherRemoteDao(HttpClient httpClient, ModelMapper modelMapper) {
        return new WeatherRemoteDaoImpl(httpClient,modelMapper);
    }

    @Provides
    @Singleton
    HttpClient providesHttpClient() {
        return new RetrofitClient();
    }

    @Provides
    ModelMapper providesModelMapper(){return new ModelMapper();}

}
