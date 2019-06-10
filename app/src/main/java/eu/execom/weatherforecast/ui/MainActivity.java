package eu.execom.weatherforecast.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

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
import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.ui.adapter.generic.DailyDataAdapter;
import eu.execom.weatherforecast.usecase.WeatherUseCase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CompositeDisposable compositeDisposable;
    private static final int ACCESS_FINE_LOCATION_PERMISSION_REQUEST = 1;

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
        checkPermissions();

        textViewCity.setOnClickListener(view -> new LovelyTextInputDialog(view.getContext(), R.color.colorPrimaryDark)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.text_location_dialog_title)
                .setIcon(R.drawable.ic_location)
                .setConfirmButton(android.R.string.ok, this::fetchWeeklyWeatherForecast)
                .show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void setBackground() {
        backgroundWeatherLayout.setBackgroundResource(R.drawable.clear_day_background);
    }

    private void showWeatherData(DailyWeather dailyWeathers) {
        imageViewWeather.setImageResource(weatherIconProvider.getWeatherIcon(dailyWeathers.getCurrently().getIcon()));
        textViewTemperature.setText(String.valueOf(converterTemperature.convertToCelsius(dailyWeathers.getCurrently())));
        textViewDescription.setText(dailyWeathers.getCurrently().getSummary());
        textViewCity.setText(dailyWeathers.getLocationData().getCityName());
        dailyDataAdapter.setItems(dailyWeathers.getDaily().getData());
        setBackground();

    }

    private void handleError(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, throwable.getMessage());
        }
        Toast.makeText(myApplication, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_PERMISSION_REQUEST);
        } else {
            fetchWeeklyWeatherForecastForCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ACCESS_FINE_LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchWeeklyWeatherForecastForCurrentLocation();
            } else {
                Toast.makeText(myApplication, "Permission denied :(", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void fetchWeeklyWeatherForecastForCurrentLocation() {
        compositeDisposable.add(weatherUseCase.getWeatherForecastForCurrentLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showWeatherData, this::handleError));
    }

    private void fetchWeeklyWeatherForecast(String cityName) {
        compositeDisposable.add(weatherUseCase.getWeatherForecast(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showWeatherData, this::handleError));
    }
}