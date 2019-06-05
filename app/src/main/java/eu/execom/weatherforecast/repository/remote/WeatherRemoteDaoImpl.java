package eu.execom.weatherforecast.repository.remote;

import org.modelmapper.ModelMapper;

import java.util.Date;

import eu.execom.weatherforecast.domain.Currently;
import eu.execom.weatherforecast.domain.Daily;
import eu.execom.weatherforecast.domain.DailyData;
import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.repository.remote.dto.CurrentlyDto;
import eu.execom.weatherforecast.repository.remote.dto.DailyDataDto;
import eu.execom.weatherforecast.repository.remote.dto.DailyDto;
import eu.execom.weatherforecast.usecase.dependency.repository.WeatherRemoteDao;
import io.reactivex.Single;

public class WeatherRemoteDaoImpl implements WeatherRemoteDao {

    private final HttpClient httpClient;

    private final ModelMapper modelMapper;

    public WeatherRemoteDaoImpl(HttpClient httpClient, ModelMapper modelMapper){
        this.httpClient = httpClient;
        this.modelMapper = modelMapper;
        setupMapper();
    }

    private void setupMapper() {
        modelMapper.createTypeMap(CurrentlyDto.class, Currently.class);

        modelMapper.createTypeMap(DailyDto.class, Daily.class);

        modelMapper.createTypeMap(DailyDataDto.class, DailyData.class);

        modelMapper.createTypeMap(Long.class, Date.class)
                .setConverter(input -> new Date(input.getSource()));
    }


    @Override
    public Single<DailyWeather> getForecast(double lat, double lon) {
        return httpClient.getWeather(lat,lon)
                .map(dailyWeatherDto -> modelMapper.map(dailyWeatherDto, DailyWeather.class));
    }




}
