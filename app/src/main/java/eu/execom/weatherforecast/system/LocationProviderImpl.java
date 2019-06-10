package eu.execom.weatherforecast.system;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

import eu.execom.weatherforecast.domain.Coordinates;
import io.nlopez.smartlocation.SmartLocation;
import io.reactivex.Single;

public class LocationProviderImpl implements LocationProvider {

    private final Context context;

    public LocationProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public Single<Location> getCurrentLocation() {
        return Single.create(emitter -> SmartLocation.with(context).location()
                .start(emitter::onSuccess));
    }

    @Override
    public Single<Coordinates> getLocationByCityName(String name) {

        return Single.create(emitter -> {
            Coordinates coordinatesByName = new Coordinates();
            final List<Coordinates> coordinatesByNameList = new ArrayList<>();
            coordinatesByNameList.add(coordinatesByName);
            SmartLocation.with(context).geocoding()
                    .direct(name, (name1, results) -> {

                        if (results.size() > 0) {
                            Location location = results.get(0).getLocation();
                            coordinatesByNameList.get(0).setLongitude(location.getLongitude());
                            coordinatesByNameList.get(0).setLatitude(location.getLatitude());
                        }
                        emitter.onSuccess(coordinatesByName);
                    });
        });
    }

    @Override
    public Single<String> getNameOfCurrentLocation(Location currentLocation) {
        return Single.create(emitter -> SmartLocation.with(context).geocoding()
                .reverse(currentLocation, (location, list) -> emitter.onSuccess(list.get(0).getLocality())));
    }
}

