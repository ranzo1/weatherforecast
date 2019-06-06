package eu.execom.weatherforecast;

import android.annotation.SuppressLint;
import android.app.Application;

import org.androidannotations.annotations.EApplication;

import eu.execom.weatherforecast.repository.remote.RemoteRepositoryModule;
import eu.execom.weatherforecast.usecase.UseCaseModule;

@SuppressLint("Registered")
@EApplication
public class MyApplication extends Application {

    private Component component;

    @Override
    public void onCreate(){
        super.onCreate();
        component = createComponent();
    }

    private Component createComponent() {
        return DaggerComponent
                .builder()
                .remoteRepositoryModule(new RemoteRepositoryModule(this))
                .useCaseModule(new UseCaseModule())
                .build();
    }

    public Component getComponent() {
        return component;
    }
}
