package eu.execom.weatherforecast.usecase.dependency.repository;

import eu.execom.weatherforecast.domain.Coordinates;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface LocalCoordinatesDao {

    Completable saveCoordinates(Coordinates coordinates, String cityName);
    Single<Coordinates> getCoordinates(String city);
}
