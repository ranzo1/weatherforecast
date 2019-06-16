package eu.execom.weatherforecast.repository.local.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import eu.execom.weatherforecast.domain.Coordinates;
import eu.execom.weatherforecast.usecase.dependency.repository.LocalCoordinatesDao;
import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalCoordinatesDaoImpl implements LocalCoordinatesDao {

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public LocalCoordinatesDaoImpl(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public Completable saveCoordinates(Coordinates coordinates, String cityName) {
        return Completable.fromAction(() -> {
            String coordinatesJsonString = gson.toJson(coordinates);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(cityName, coordinatesJsonString).apply();
        });
    }

    @Override
    public Single<Coordinates> getCoordinates(String city) {
        return Single.create(emitter -> {
            String coordinatesJsonString = sharedPreferences.getString(city, "");
            if (!coordinatesJsonString.isEmpty()) {
                Coordinates coordinates = gson.fromJson(coordinatesJsonString, Coordinates.class);
                emitter.onSuccess(coordinates);
            } else {
                emitter.onError(new Throwable("Error while loading data"));
            }
        });
    }
}