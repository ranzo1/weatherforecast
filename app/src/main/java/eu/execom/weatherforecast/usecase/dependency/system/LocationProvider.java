package eu.execom.weatherforecast.usecase.dependency.system;

import eu.execom.weatherforecast.domain.Coordinates;
import io.reactivex.Single;

public interface LocationProvider {

    Single<Coordinates> getCurrentLocation();

}
