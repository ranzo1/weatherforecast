package eu.execom.weatherforecast;


import javax.inject.Singleton;

import eu.execom.weatherforecast.repository.local.LocalRepositoryModule;
import eu.execom.weatherforecast.repository.remote.RemoteRepositoryModule;
import eu.execom.weatherforecast.system.SystemModule;
import eu.execom.weatherforecast.ui.MainActivity;
import eu.execom.weatherforecast.usecase.UseCaseModule;

@Singleton
@dagger.Component(modules = {UseCaseModule.class, RemoteRepositoryModule.class, SystemModule.class, LocalRepositoryModule.class})
public interface Component {

    void inject(MainActivity mainActivity);
}