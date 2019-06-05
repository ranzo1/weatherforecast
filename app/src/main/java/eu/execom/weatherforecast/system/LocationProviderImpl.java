package eu.execom.weatherforecast.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import eu.execom.weatherforecast.domain.Coordinates;
import eu.execom.weatherforecast.usecase.dependency.system.LocationProvider;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LocationProviderImpl implements LocationProvider {

    private final Context context;

    public LocationProviderImpl(Context context) {
        this.context = context;
    }

    @Override
    public Single<Coordinates> getCurrentLocation() {

        return Single.create(new SingleOnSubscribe<Coordinates>() {
            @SuppressLint("MissingPermission")
            @Override
            public void subscribe(final SingleEmitter<Coordinates> emitter) throws Exception {
                FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);





               // final String[] locationString = new String[1];
                client.getLastLocation().addOnCompleteListener(location -> {
                    if (location.isSuccessful() && location.getResult() != null) {
                        //locationString[0] = location.getResult().toString();
                        emitter.onSuccess(new Coordinates(130,
                                50));
                    } else {
                       // emitter.onError(new Exception("Location not available"));
                        emitter.onSuccess(new Coordinates(130,
                                50));
                    }
                });
            }
        });





    }
}
