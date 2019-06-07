package eu.execom.weatherforecast.system;

import android.content.Context;
import android.util.Log;

import eu.execom.weatherforecast.domain.Coordinates;
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
                    Log.d("test", "onLocationUpdated " + Thread.currentThread().getName());
                    emitter.onSuccess(new Coordinates(location.getLongitude(),location.getLatitude()));
                }));
    }
}
