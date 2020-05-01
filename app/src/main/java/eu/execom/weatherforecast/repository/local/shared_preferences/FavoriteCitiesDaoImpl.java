package eu.execom.weatherforecast.repository.local.shared_preferences;

import android.content.Context;

import java.util.ArrayList;

import eu.execom.weatherforecast.usecase.dependency.repository.FavoriteCitiesDao;

public class FavoriteCitiesDaoImpl implements FavoriteCitiesDao {
    private static final String TAG = "FavouriteCities";
    private final static String FAVORITE_CITIES_KEY = "favoriteCities";
    private TinyDB tinyDB;

    public FavoriteCitiesDaoImpl(Context context) {
        tinyDB = new TinyDB(context);
    }

    @Override
    public void addCityToFavourites(String city) {
        ArrayList<String> favoriteCities;
        if (tinyDB.getListString(FAVORITE_CITIES_KEY) != null) {
            favoriteCities = tinyDB.getListString(FAVORITE_CITIES_KEY);
            if (!favoriteCities.contains(city)) {
                favoriteCities.add(city);
                tinyDB.putListString(FAVORITE_CITIES_KEY, favoriteCities);
            }
        } else {
            favoriteCities = new ArrayList<>();
            favoriteCities.add(city);
            tinyDB.putListString(FAVORITE_CITIES_KEY, favoriteCities);
        }
    }

    @Override
    public void removeCityFromFavourites(String city) {
        ArrayList<String> favoriteCities;
        if (tinyDB.getListString(FAVORITE_CITIES_KEY) != null) {
            favoriteCities = tinyDB.getListString(FAVORITE_CITIES_KEY);
            if (isFavouriteCity(city)) {
                favoriteCities.remove(city);
                tinyDB.putListString(FAVORITE_CITIES_KEY, favoriteCities);
            }
        }
    }

    @Override
    public boolean isFavouriteCity(String city) {
        ArrayList<String> favouriteCities = tinyDB.getListString(FAVORITE_CITIES_KEY);
        return favouriteCities.contains(city.toLowerCase()) || favouriteCities.contains(city.toUpperCase())
                || favouriteCities.contains(city);
    }

    @Override
    public ArrayList<String> getFavouriteCities() {
        return tinyDB.getListString(FAVORITE_CITIES_KEY);
    }
}
