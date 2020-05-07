package eu.execom.weatherforecast.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.like.LikeButton;
import com.wang.avi.AVLoadingIndicatorView;
import com.yarolegovich.lovelydialog.LovelyChoiceDialog;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import eu.execom.weatherforecast.BuildConfig;
import eu.execom.weatherforecast.MyApplication;
import eu.execom.weatherforecast.R;
import eu.execom.weatherforecast.TemperatureConverter;
import eu.execom.weatherforecast.domain.DailyData;
import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.ui.adapter.daily.DailyDataAdapter;
import eu.execom.weatherforecast.ui.adapter.daily.DailyDataItemView;
import eu.execom.weatherforecast.ui.adapter.hourly.HourlyDataAdapter;
import eu.execom.weatherforecast.usecase.WeatherUseCase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements DailyDataItemView.DailyDataItemActionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CompositeDisposable compositeDisposable;
    private static final int ACCESS_FINE_LOCATION_PERMISSION_REQUEST = 1;

    @Bean
    WeatherDrawableProvider weatherDrawableProvider;

    @Bean
    TemperatureConverter temperatureConverter;

    @Bean
    DateFormatter dateFormatter;

    @Bean
    WeatherPercentageFormatter percentageFormatter;

    @App
    MyApplication myApplication;

    @Inject
    WeatherUseCase weatherUseCase;

    @Bean
    DailyDataAdapter dailyDataAdapter;

    @Bean
    HourlyDataAdapter hourlyDataAdapter;

    @ViewById
    TextView textViewTemperature;

    @ViewById
    TextView chooseCity;

    @ViewById
    TextView textViewDescription;

    @ViewById
    TextView textViewSyncTime;

    @ViewById
    TextView temperatureHighest;

    @ViewById
    TextView temperatureLowest;

    @ViewById
    TextView uvIndex;

    @ViewById
    TextView sunrise;

    @ViewById
    TextView sunset;

    @ViewById
    TextView humidity;

    @ViewById
    RecyclerView recyclerWeather;

    @ViewById
    RecyclerView recyclerWeatherHourly;

    @ViewById
    RelativeLayout backgroundWeatherLayout;

    @ViewById
    ImageView imageViewWeather;

    @ViewById
    LikeButton addCityToFavoritesButton;

    @ViewById
    ImageView favoriteCities;

    @ViewById
    ImageView imageViewTemperature;

    @ViewById
    ImageView currentLocation;

    @ViewById
    AVLoadingIndicatorView avi;

    @ViewById
    RelativeLayout container;

    @ViewById
    CardView cardViewHourly;

    @ViewById
    CardView cardViewDaily;

    @ViewById
    CardView cardViewAdditionalData;

    @AfterViews
    void init() {
        container.setVisibility(View.INVISIBLE);
        myApplication.getComponent().inject(this);
        getSupportActionBar().hide();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        avi.show();
        recyclerWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        recyclerWeather.setAdapter(dailyDataAdapter);
        dailyDataAdapter.setDailyDataItemActionListener(this);
        recyclerWeatherHourly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerWeatherHourly.setAdapter(hourlyDataAdapter);
        compositeDisposable = new CompositeDisposable();
        checkPermissions();
    }

    @Click
    void favoriteCities() {
        new LovelyChoiceDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.choose_favorite_place)
                .setIcon(R.drawable.ic_favourite_location)
                .setMessage(R.string.display_weather_for_chosen_place)
                .setItems(getFavouriteCities(), this::onFavoriteCitySelected)
                .show();
    }

    @Click
    void chooseCity() {
        new LovelyTextInputDialog(this)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle(R.string.choose_place)
                .setMessage(R.string.display_weather_for_chosen_place)
                .setIcon(R.drawable.ic_location)
                .setConfirmButton(android.R.string.ok, MainActivity.this::fetchWeeklyWeatherForecast)
                .show();
    }

    @Click
    void currentLocation() {
        avi.setVisibility(View.VISIBLE);
        avi.show();
        container.setVisibility(View.INVISIBLE);
        fetchWeeklyWeatherForecastForCurrentLocation();
    }

    @Click
    void addCityToFavoritesButton() {
        if (!addCityToFavoritesButton.isLiked()) {
            if (!isFavouriteCity(chooseCity.getText().toString())) {
                addCityToFavoritesButton.setLiked(true);
                addCityToFavourites(chooseCity.getText().toString());
                Toast.makeText(myApplication, R.string.addCityToFavourites, Toast.LENGTH_SHORT).show();
            }
        } else {
            addCityToFavoritesButton.setLiked(false);
            removeCityFromFavourites(chooseCity.getText().toString());
            Toast.makeText(myApplication, R.string.removeCityFromFavourites, Toast.LENGTH_SHORT).show();
        }
    }

    private void addCityToFavourites(String city) {
        weatherUseCase.addCitiesToFavorites(city);
    }

    private void removeCityFromFavourites(String city) {
        weatherUseCase.removeCityFromFavorites(city);
    }

    private boolean isFavouriteCity(String city) {
        return weatherUseCase.isFavouriteCity(city);
    }

    private ArrayList<String> getFavouriteCities() {
        return weatherUseCase.getFavouriteCities();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    private void animate() {
        List<Integer> views = new ArrayList<>();
        views.add(R.id.cardViewHourly);
        views.add(R.id.cardViewDaily);
        views.add(R.id.imageViewWeather);
        views.add(R.id.cardViewAdditionalData);

        for (int id : views) {
            YoYo.with(Techniques.FadeInRight)
                    .duration(1000)
                    .repeat(0)
                    .playOn(findViewById(id));
        }
    }

    private void showWeatherData(DailyWeather dailyWeathers) {
        List<DailyData> dailyDataList = dailyWeathers.getDaily().getData();
        String humidityPercentage = percentageFormatter.getPercentage(dailyDataList.get(0).getHumidity()) + "%";
        String temperatureCelsius = temperatureConverter.convertToCelsius(dailyWeathers.getCurrently().getTemperature()) + "°";

        imageViewWeather.setImageResource(weatherDrawableProvider.getWeatherIcons(dailyWeathers.getCurrently().getIcon()));
        textViewTemperature.setText(temperatureCelsius);
        textViewDescription.setText(dailyWeathers.getCurrently().getSummary());
        textViewSyncTime.setText(String.valueOf(dateFormatter.toDateAndTime(dailyWeathers.getLastTimeSync())));
        chooseCity.setText(dailyWeathers.getLocationData().getCityName());
        uvIndex.setText(String.valueOf((dailyDataList.get(0).getUvIndex())));
        if (dailyDataList.get(0).getSunriseTime() != null && dailyDataList.get(0).getSunsetTime() != null) {
            sunrise.setText(dateFormatter.toHour(dailyDataList.get(0).getSunriseTime()));
            sunset.setText(dateFormatter.toHour(dailyDataList.get(0).getSunsetTime()));
        } else {
            sunrise.setText(R.string.no_data);
            sunset.setText(R.string.no_data);
        }
        humidity.setText(humidityPercentage);
        animate();

        if (isFavouriteCity(chooseCity.getText().toString())) {
            addCityToFavoritesButton.setLiked(true);
        } else {
            addCityToFavoritesButton.setLiked(false);
        }
        imageViewTemperature.setImageResource(weatherDrawableProvider.getTemperatureImage(temperatureConverter.convertToCelsius(dailyWeathers.getCurrently().getTemperature())));
        dailyDataAdapter.setItems(dailyDataList);
        hourlyDataAdapter.setItems(dailyWeathers.getHourly().getData());
        backgroundWeatherLayout.setBackgroundResource(weatherDrawableProvider.getWeatherBackground(dailyWeathers.getCurrently().getIcon()));
        avi.hide();
        container.setVisibility(View.VISIBLE);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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

    @Override
    public void onItemClick(DailyData dailyData) {
        SingleDayForecastActivity_.intent(this).dailyData(dailyData).start();
    }

    private void onFavoriteCitySelected(int position, String city) {
        fetchWeeklyWeatherForecast(city);
    }
}