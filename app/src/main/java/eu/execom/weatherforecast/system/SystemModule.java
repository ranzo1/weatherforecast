package eu.execom.weatherforecast.system;
import android.content.Context;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import eu.execom.weatherforecast.usecase.dependency.repository.LocationProvider;

@Module
public class SystemModule {

    private final Context context;

    public SystemModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    LocationProvider provideLocationProvider (){
        return new LocationProviderImpl(context);
    }
}
