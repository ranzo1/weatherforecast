package eu.execom.weatherforecast.usecase.dependency.repository;

import java.util.ArrayList;

public interface FavoriteCitiesDao {
    void addCityToFavourites(String city);

    void removeCityFromFavourites(String city);

    boolean isFavouriteCity(String city);

    ArrayList<String> getFavouriteCities();
}
