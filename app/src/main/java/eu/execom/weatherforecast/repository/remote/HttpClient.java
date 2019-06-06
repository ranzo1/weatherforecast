package eu.execom.weatherforecast.repository.remote;

import eu.execom.weatherforecast.repository.remote.dto.DailyWeatherDto;
import io.reactivex.Single;

public interface HttpClient {

    Single<DailyWeatherDto> getWeather(double latitude, double longitude);
}
