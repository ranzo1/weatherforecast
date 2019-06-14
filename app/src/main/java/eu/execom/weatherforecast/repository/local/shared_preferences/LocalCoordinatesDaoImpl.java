package eu.execom.weatherforecast.repository.local.shared_preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import eu.execom.weatherforecast.domain.Coordinates;
import eu.execom.weatherforecast.usecase.dependency.repository.LocalCoordinatesDao;
import io.reactivex.Completable;
import io.reactivex.Single;

import static android.content.Context.MODE_PRIVATE;

public class LocalCoordinatesDaoImpl implements LocalCoordinatesDao {

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    final static String COORDINATES = "coordinates";
    private Gson gson = new Gson();

    @SuppressLint("CommitPrefEdits")
    public LocalCoordinatesDaoImpl(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(COORDINATES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public Completable saveCoordinates(Coordinates coordinates, String cityName) {
        return Completable.fromAction(() -> {
            String coordinatesJsonString = gson.toJson(coordinates);
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