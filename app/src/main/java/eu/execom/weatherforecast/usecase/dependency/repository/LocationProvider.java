package eu.execom.weatherforecast.usecase.dependency.repository;

import android.location.Location;

import eu.execom.weatherforecast.domain.Coordinates;
import io.reactivex.Single;

public interface LocationProvider {

    Single<Coordinates> getCurrentLocation();
    Single<Coordinates> getLocationByCityName(String name);
    Single<String> getNameOfLocation(Coordinates coordinates);
}
