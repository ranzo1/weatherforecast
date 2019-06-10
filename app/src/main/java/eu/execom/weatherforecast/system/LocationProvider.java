package eu.execom.weatherforecast.system;

import android.location.Location;

import eu.execom.weatherforecast.domain.Coordinates;
import io.reactivex.Single;

public interface LocationProvider {

    Single<Location> getCurrentLocation();
    Single<Coordinates> getLocationByCityName(String name);
    Single<String> getNameOfCurrentLocation(Location location);
}
