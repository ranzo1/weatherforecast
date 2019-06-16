package eu.execom.weatherforecast.repository.local.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.usecase.dependency.repository.LocalWeatherDao;
import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalWeatherDaoImpl implements LocalWeatherDao {

    private final static String CURRENT_LOCATION_KEY = "currentLocation";
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public LocalWeatherDaoImpl(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public Completable saveDailyWeather(DailyWeather dailyWeather, String city) {
        return Completable.fromAction(() -> {
            String dailyWeatherJsonString = gson.toJson(dailyWeather);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(city, dailyWeatherJsonString).apply();
        });
    }

    @Override
    public Completable saveDailyWeatherForCurrentLocation(DailyWeather dailyWeather) {
        return Completable.fromAction(() -> {
            String dailyWeatherJsonString = gson.toJson(dailyWeather);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(CURRENT_LOCATION_KEY, dailyWeatherJsonString).apply();
        });
    }

    @Override
    public Single<DailyWeather> getForecast(String city) {
        return Single.create(emitter -> {
            String dailyWeatherJsonString = sharedPreferences.getString(city, "");
            if (!dailyWeatherJsonString.isEmpty()) {
                DailyWeather dailyWeather = gson.fromJson(dailyWeatherJsonString, DailyWeather.class);
                emitter.onSuccess(dailyWeather);
            } else {
                emitter.onError(new Throwable("Error while loading data"));
            }
        });
    }

    @Override
    public Single<DailyWeather> getForecastForCurrentLocation() {
        return Single.create(emitter -> {
            String dailyWeatherJsonString = sharedPreferences.getString(CURRENT_LOCATION_KEY, "");
            if (!dailyWeatherJsonString.isEmpty()) {
                DailyWeather dailyWeather = gson.fromJson(dailyWeatherJsonString, DailyWeather.class);
                emitter.onSuccess(dailyWeather);
            } else {
                emitter.onError(new Throwable("Error while loading data."));
            }
        });
    }
}