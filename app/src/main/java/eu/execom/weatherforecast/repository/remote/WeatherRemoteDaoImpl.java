package eu.execom.weatherforecast.repository.remote;

import org.modelmapper.ModelMapper;

import java.util.Date;

import eu.execom.weatherforecast.domain.Currently;
import eu.execom.weatherforecast.domain.Daily;
import eu.execom.weatherforecast.domain.DailyData;
import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.domain.WeatherType;
import eu.execom.weatherforecast.repository.remote.dto.CurrentlyDto;
import eu.execom.weatherforecast.repository.remote.dto.DailyDataDto;
import eu.execom.weatherforecast.repository.remote.dto.DailyDto;
import eu.execom.weatherforecast.usecase.dependency.repository.WeatherRemoteDao;
import io.reactivex.Single;

public class WeatherRemoteDaoImpl implements WeatherRemoteDao {

    private final HttpClient httpClient;
    private final ModelMapper modelMapper;

    public WeatherRemoteDaoImpl(HttpClient httpClient, ModelMapper modelMapper) {
        this.httpClient = httpClient;
        this.modelMapper = modelMapper;
        setupMapper();
    }

    private void setupMapper() {
        modelMapper.createTypeMap(CurrentlyDto.class, Currently.class);

        modelMapper.createTypeMap(DailyDto.class, Daily.class);

        modelMapper.createTypeMap(DailyDataDto.class, DailyData.class);

        modelMapper.createTypeMap(Long.class, Date.class)
                .setConverter(input -> new Date(input.getSource() * 1000));

        modelMapper.createTypeMap(String.class, WeatherType.class)
                .setConverter(context -> {
                    switch (context.getSource()) {
                        case "clear-day":
                            return WeatherType.CLEAR_DAY;
                        case "clear-night":
                            return WeatherType.CLEAR_NIGHT;
                        case "rain":
                            return WeatherType.RAIN;
                        case "snow":
                            return WeatherType.SNOW;
                        case "sleet":
                            return WeatherType.SLEET;
                        case "wind":
                            return WeatherType.WIND;
                        case "fog":
                            return WeatherType.FOG;
                        case "cloudy":
                            return WeatherType.CLOUDY;
                        case "partly-cloudy-day":
                            return WeatherType.PARTLY_CLOUDY_DAY;
                        case "partly-cloudy-night":
                            return WeatherType.PARTLY_CLOUDY_NIGHT;
                        case "hail":
                            return WeatherType.HAIL;
                        case "thunderstorm":
                            return WeatherType.THUNDERSTORM;
                        case "tornado":
                            return WeatherType.TORNADO;
                    }
                    throw new IllegalArgumentException("Unsupported weather type: " + context.getSource());
                });
    }

    @Override
    public Single<DailyWeather> getForecast(double latitude, double longitude) {
        return httpClient.getWeather(latitude, longitude)
                .map(dailyWeatherDto -> modelMapper.map(dailyWeatherDto, DailyWeather.class));
    }
}