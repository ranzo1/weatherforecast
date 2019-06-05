package eu.execom.weatherforecast.repository.remote.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import eu.execom.weatherforecast.repository.remote.NetworkManager;
import okhttp3.Interceptor;
import okhttp3.Response;

public class NoNetworkException implements Interceptor {

    private final NetworkManager networkManager;;

    public NoNetworkException(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Response response;

        if(!networkManager.isNetworkAvailable()){


        }
        return null;
    }
}
