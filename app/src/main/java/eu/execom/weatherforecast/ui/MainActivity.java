package eu.execom.weatherforecast.ui;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import eu.execom.weatherforecast.BuildConfig;
import eu.execom.weatherforecast.ConverterTemperature;
import eu.execom.weatherforecast.MyApplication;
import eu.execom.weatherforecast.R;
import eu.execom.weatherforecast.WeatherIconProvider;
import eu.execom.weatherforecast.domain.Coordinates;
import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.ui.adapter.generic.DailyDataAdapter;
import eu.execom.weatherforecast.usecase.WeatherUseCase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CompositeDisposable compositeDisposable;
    private double latitude = 45.267136;
    private double longitude = 19.833549;

    @Bean
    WeatherIconProvider weatherIconProvider;
    @Bean
    ConverterTemperature converterTemperature;
    @App
    MyApplication myApplication;
    @Inject
    WeatherUseCase weatherUseCase;
    @Bean
    DailyDataAdapter dailyDataAdapter;
    @ViewById
    TextView textViewTemperature;
    @ViewById
    TextView textViewCity;
    @ViewById
    TextView textViewDescription;
    @ViewById
    RecyclerView recyclerWeather;
    @ViewById
    RelativeLayout backgroundWeatherLayout;
    @ViewById
    ImageView imageViewWeather;

    @AfterViews
    void init() {
        myApplication.getComponent().inject(this);
        requestPermission();
        getSupportActionBar().hide();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        recyclerWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        recyclerWeather.setAdapter(dailyDataAdapter);
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(weatherUseCase.getWeatherForecastForCurrentLocation(new Coordinates(longitude, latitude))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DailyWeather>() {
                    @Override
                    public void accept(DailyWeather dailyWeathers) throws Exception {

                        dailyDataAdapter.setItems(dailyWeathers.getDaily().getData());
                        setBackground();
                        imageViewWeather.setImageResource(weatherIconProvider.getWeatherIcon(dailyWeathers.getCurrently().getIcon()));
                        textViewTemperature.setText(String.valueOf(converterTemperature.convertToCelsius(dailyWeathers.getCurrently())));
                        textViewDescription.setText(dailyWeathers.getCurrently().getSummary());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (BuildConfig.DEBUG) {
                            Log.d(TAG, throwable.getMessage());
                        }

                        Toast.makeText(myApplication, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void requestPermission() {

        String fineLocationPermission = "android.permission.ACCESS_FINE_LOCATION";
        String coarseLocationPermission = "android.permission.ACCESS_COARSE_LOCATION";
        int checkValueFineLocationPermission = this.checkCallingOrSelfPermission(fineLocationPermission);
        int checkValueCoarseLocationPermission = this.checkCallingOrSelfPermission(coarseLocationPermission);

        if(checkValueFineLocationPermission==PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
        }else{
            Toast.makeText(myApplication, "Permission denied.", Toast.LENGTH_SHORT).show();
        }

        if(checkValueCoarseLocationPermission==PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION}, 1);
        }else{
            Toast.makeText(myApplication, "Permission denied.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setBackground() {

        backgroundWeatherLayout.setBackgroundResource(R.drawable.night);
    }
}
