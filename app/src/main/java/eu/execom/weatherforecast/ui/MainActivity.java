package eu.execom.weatherforecast.ui;

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
import eu.execom.weatherforecast.ui.adapter.generic.DailyDataAdapter;
import eu.execom.weatherforecast.usecase.WeatherUseCase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CompositeDisposable compositeDisposable;
    private final double CITY_NOVI_SAD_LATITUDE  = 45.267136;
    private final double CITY_NOVI_SAD_LONGITUDE = 19.833549;

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
        getSupportActionBar().hide();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        recyclerWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        recyclerWeather.setAdapter(dailyDataAdapter);
        compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(weatherUseCase.getWeatherForecastForCurrentLocation(new Coordinates(CITY_NOVI_SAD_LONGITUDE, CITY_NOVI_SAD_LATITUDE))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dailyWeathers -> {
                    dailyDataAdapter.setItems(dailyWeathers.getDaily().getData());
                    setBackground();
                    imageViewWeather.setImageResource(weatherIconProvider.getWeatherIcon(dailyWeathers.getCurrently().getIcon()));
                    textViewTemperature.setText(String.valueOf(converterTemperature.convertToCelsius(dailyWeathers.getCurrently())));
                    textViewDescription.setText(dailyWeathers.getCurrently().getSummary());
                }, throwable -> {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, throwable.getMessage());
                    }
                    Toast.makeText(myApplication, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void setBackground() {
        backgroundWeatherLayout.setBackgroundResource(R.drawable.night);
    }
}