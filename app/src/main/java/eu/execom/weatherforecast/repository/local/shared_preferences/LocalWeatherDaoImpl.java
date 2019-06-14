package eu.execom.weatherforecast.repository.local.shared_preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.usecase.dependency.repository.LocalWeatherDao;
import io.reactivex.Completable;
import io.reactivex.Single;

import static android.content.Context.MODE_PRIVATE;

public class LocalWeatherDaoImpl implements LocalWeatherDao {

    private final Context context;
    final static String DAILY_WEATHER = "DailyWeather";
    final static String CURRENT_LOCATION_KEY = "currentLocation";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();

    @SuppressLint("CommitPrefEdits")
    public LocalWeatherDaoImpl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(DAILY_WEATHER, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public Completable saveDailyWeather(DailyWeather dailyWeather, String city) {
        return Completable.fromAction(() -> {
            String dailyWeatherJsonString = gson.toJson(dailyWeather);
            editor.putString(city, dailyWeatherJsonString).apply();
        });
    }

    @Override
    public Completable saveDailyWeatherForCurrentLocation(DailyWeather dailyWeather) {
        return Completable.fromAction(() -> {
            String dailyWeatherJsonString = gson.toJson(dailyWeather);
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