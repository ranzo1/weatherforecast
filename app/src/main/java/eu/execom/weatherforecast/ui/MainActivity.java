package eu.execom.weatherforecast.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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
    private static final String FAHRENHEIT = "fahrenheit";
    private static final String CELSIUS = "celsius";

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

    @Bean
    DescriptionWeatherProvider descriptionWeatherProvider;

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
    TextView minutelySummary;

    @ViewById
    TextView textViewWind;

    @ViewById
    TextView textViewWindSpeed;

    @ViewById
    TextView textViewPrecipProbability;

    @ViewById
    RecyclerView recyclerWeatherDaily;

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

    @ViewById
    CardView cardViewMinutely;

    @ViewById
    LottieAnimationView wind_animation_view;

    @ViewById
    LottieAnimationView rain_animation_view;

    @ViewById
    SwitchCompat toggleButton;

    @AfterViews
    void init() {
        container.setVisibility(View.INVISIBLE);
        myApplication.getComponent().inject(this);
        getSupportActionBar().hide();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        descriptionWeatherProvider.setContext(this);
        avi.show();
        recyclerWeatherDaily.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerWeatherDaily.setAdapter(dailyDataAdapter);
        dailyDataAdapter.setDailyDataItemActionListener(this);
        recyclerWeatherHourly.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerWeatherHourly.setAdapter(hourlyDataAdapter);
        compositeDisposable = new CompositeDisposable();
        checkPermissions();

        wind_animation_view.playAnimation();
        rain_animation_view.playAnimation();

        toggleButton.setTextOn(getString(R.string.fahrenheitUnit));
        toggleButton.setTextOff(getString(R.string.celsiusUnit));

        setTemperatureUnitToViews();
        toggleButton.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            float temperature = Float.parseFloat(textViewTemperature.getText().toString());
            if (isChecked) {
                weatherUseCase.setTemperatureUnit(FAHRENHEIT);
                textViewTemperature.setText(String.valueOf(temperatureConverter.convertToFahrenheit(temperature)));
                hourlyDataAdapter.setTemperatureUnit(FAHRENHEIT);
                hourlyDataAdapter.notifyDataSetChanged();
                dailyDataAdapter.setTemperatureUnit(FAHRENHEIT);
                dailyDataAdapter.notifyDataSetChanged();

            } else {
                weatherUseCase.setTemperatureUnit(CELSIUS);
                textViewTemperature.setText(String.valueOf(temperatureConverter.convertToCelsius(temperature)));
                hourlyDataAdapter.setTemperatureUnit(CELSIUS);
                hourlyDataAdapter.notifyDataSetChanged();
                dailyDataAdapter.setTemperatureUnit(CELSIUS);
                dailyDataAdapter.notifyDataSetChanged();
            }
        });
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

    private void setTemperatureUnitToViews() {
        if (weatherUseCase.getTemperatureUnit().equals(FAHRENHEIT)) {
            toggleButton.setChecked(true);
            dailyDataAdapter.setTemperatureUnit(FAHRENHEIT);
            hourlyDataAdapter.setTemperatureUnit(FAHRENHEIT);
            return;
        }
        if (weatherUseCase.getTemperatureUnit().equals(CELSIUS)) {
            toggleButton.setChecked(false);
            dailyDataAdapter.setTemperatureUnit(CELSIUS);
            hourlyDataAdapter.setTemperatureUnit(CELSIUS);
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
        views.add(R.id.cardViewMinutely);

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

        imageViewWeather.setImageResource(weatherDrawableProvider.getWeatherIcons(dailyWeathers.getCurrently().getIcon()));
        setTemperatureUnitToViews(dailyWeathers);
        textViewDescription.setText(dailyWeathers.getCurrently().getSummary());
        textViewSyncTime.setText(String.valueOf(dateFormatter.toDateAndTime(dailyWeathers.getLastTimeSync())));
        chooseCity.setText(dailyWeathers.getLocationData().getCityName());
        uvIndex.setText(String.valueOf((dailyDataList.get(0).getUvIndex())));
        textViewWindSpeed.setText(String.valueOf(dailyWeathers.getCurrently().getWindSpeed()));
        textViewWind.setText(descriptionWeatherProvider.getWindType(dailyWeathers.getCurrently().getWindSpeed()));
        if (dailyDataList.get(0).getSunriseTime() != null && dailyDataList.get(0).getSunsetTime() != null) {
            sunrise.setText(dateFormatter.toHour(dailyDataList.get(0).getSunriseTime()));
            sunset.setText(dateFormatter.toHour(dailyDataList.get(0).getSunsetTime()));
        } else {
            sunrise.setText(R.string.no_data);
            sunset.setText(R.string.no_data);
        }
        humidity.setText(humidityPercentage);

        if (isFavouriteCity(chooseCity.getText().toString())) {
            addCityToFavoritesButton.setLiked(true);
        } else {
            addCityToFavoritesButton.setLiked(false);
        }
        imageViewTemperature.setImageResource(weatherDrawableProvider.getTemperatureInCelsiusImage(temperatureConverter.convertToCelsius(dailyWeathers.getCurrently().getTemperature())));
        dailyDataAdapter.setItems(dailyDataList);
        hourlyDataAdapter.setItems(dailyWeathers.getHourly().getData());
        backgroundWeatherLayout.setBackgroundResource(weatherDrawableProvider.getWeatherBackground(dailyWeathers.getCurrently().getIcon()));
        if (dailyWeathers.getMinutely() != null) {
            minutelySummary.setText(dailyWeathers.getMinutely().getSummary());
        } else {
            minutelySummary.setText(R.string.no_data_to_show);
        }
        animate();
        avi.hide();
        container.setVisibility(View.VISIBLE);
    }

    private void setTemperatureUnitToViews(DailyWeather dailyWeather) {
        int temperatureFahrenheit = Math.round(dailyWeather.getCurrently().getTemperature());
        int temperatureCelsius = temperatureConverter.convertToCelsius(dailyWeather.getCurrently().getTemperature());
        if (weatherUseCase.getTemperatureUnit().equals(CELSIUS)) {
            textViewTemperature.setText(String.valueOf(temperatureCelsius));
            return;
        }
        if (weatherUseCase.getTemperatureUnit().equals(FAHRENHEIT)) {
            textViewTemperature.setText(String.valueOf(temperatureFahrenheit));
        }
    }

    private void handleError(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, throwable.getMessage());
        }
        Toast.makeText(myApplication, R.string.error_message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(myApplication, R.string.permission_denied, Toast.LENGTH_LONG).show();
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