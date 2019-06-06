package eu.execom.weatherforecast.repository.remote;

import eu.execom.weatherforecast.repository.remote.dto.DailyWeatherDto;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BackendApiService {

    @GET("forecast/{key}/{latitude},{longitude}")
    Single<DailyWeatherDto> getWeather(@Path("key") String key, @Path("latitude") double lat,@Path("longitude") double lon);
}
