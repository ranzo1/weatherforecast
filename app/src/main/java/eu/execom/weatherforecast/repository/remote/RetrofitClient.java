package eu.execom.weatherforecast.repository.remote;

import javax.inject.Inject;

import eu.execom.weatherforecast.repository.remote.dto.DailyWeatherDto;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient implements HttpClient{

    private static final String API_URL = "https://api.darksky.net/";
    private BackendApiService service;

    @Inject
    public RetrofitClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor);
        OkHttpClient client = okHttpBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        service=retrofit.create(BackendApiService.class);
    }

    public BackendApiService getService() {
        return service;
    }

    @Override
    public Single<DailyWeatherDto> getWeather(double lat, double lon) {
        return getService().getWeather("a2432e48034ec0dd6fa038881e6bd1b1",lat,lon);
    }
}