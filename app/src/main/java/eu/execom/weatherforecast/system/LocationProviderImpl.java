package eu.execom.weatherforecast.system;

import android.content.Context;
import android.location.Location;

import java.util.List;

import eu.execom.weatherforecast.domain.Coordinates;
import eu.execom.weatherforecast.usecase.dependency.repository.LocationProvider;
import io.nlopez.smartlocation.OnGeocodingListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.geocoding.utils.LocationAddress;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class LocationProviderImpl implements LocationProvider {

    private final Context context;

    public LocationProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public Single<Coordinates> getCurrentLocation() {
        return Single.create(emitter -> SmartLocation.with(context).location()
                .start(location -> {
                    Coordinates coordinates = new Coordinates(location.getLongitude(),location.getLatitude());
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
                        }
                        emitter.onSuccess(coordinatesByName);
                    });
        });
    }

    @Override
    public Single<String> getNameOfLocation(Coordinates currentCoordinates) {
        Location currentLocation = new Location("");
        currentLocation.setLatitude(currentCoordinates.getLatitude());
        currentLocation.setLongitude(currentCoordinates.getLongitude());
        return Single.create(emitter -> SmartLocation.with(context).geocoding()
                .reverse(currentLocation, (location, list) -> emitter.onSuccess(list.get(0).getLocality())));
    }
}

