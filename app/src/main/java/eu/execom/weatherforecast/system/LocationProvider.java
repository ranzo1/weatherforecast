package eu.execom.weatherforecast.system;

import eu.execom.weatherforecast.domain.Coordinates;
import io.reactivex.Single;

public interface LocationProvider {

    Single<Coordinates> getCurrentLocation();
}
