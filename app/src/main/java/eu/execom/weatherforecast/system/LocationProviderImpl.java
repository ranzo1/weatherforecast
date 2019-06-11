package eu.execom.weatherforecast.system;

import android.content.Context;
import android.location.Location;

import eu.execom.weatherforecast.domain.Coordinates;
import eu.execom.weatherforecast.usecase.dependency.repository.LocationProvider;
import io.nlopez.smartlocation.SmartLocation;
import io.reactivex.Single;

public class LocationProviderImpl implements LocationProvider {

    private final Context context;

    public LocationProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public Single<Coordinates> getCurrentLocation() {
        return Single.create(emitter -> SmartLocation.with(context).location()
                .start(location -> {
                    Coordinates coordinates = new Coordinates(location.getLongitude(), location.getLatitude());
                    emitter.onSuccess(coordinates);
                }));
    }

    @Override
    public Single<Coordinates> getLocationByCityName(String name) {
        return Single.create(emitter -> {
            Coordinates coordinatesByName = new Coordinates();
            SmartLocation.with(context).geocoding()
                    .direct(name, (cityName, results) -> {
                        if (results.size() > 0) {
                            Location location = results.get(0).getLocation();
                            coordinatesByName.setLongitude(location.getLongitude());
                            coordinatesByName.setLatitude(location.getLatitude());
                            emitter.onSuccess(coordinatesByName);
                        } else {
                            emitter.onError(new Throwable("Wrong name of location!"));
                        }
                    });
        });
    }

    @Override
    public Single<String> getNameOfLocation(Coordinates currentCoordinates) {
        Location currentLocation = new Location("");
        currentLocation.setLatitude(currentCoordinates.getLatitude());
        currentLocation.setLongitude(currentCoordinates.getLongitude());
        return Single.create(emitter -> SmartLocation.with(context).geocoding()
                .reverse(currentLocation, (location, list) -> {
                    if (list.size() > 0) {
                        emitter.onSuccess(list.get(0).getLocality());
                    } else {
                        emitter.onError(new Throwable("List of address is empty"));
                    }
                }));
    }
}

