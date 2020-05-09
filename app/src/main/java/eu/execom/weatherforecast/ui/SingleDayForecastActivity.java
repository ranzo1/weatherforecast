package eu.execom.weatherforecast.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import eu.execom.weatherforecast.TemperatureConverter;
import eu.execom.weatherforecast.R;
import eu.execom.weatherforecast.domain.DailyData;
import eu.execom.weatherforecast.domain.LocationData;

@EActivity(R.layout.activity_single_day_forecast)
public class SingleDayForecastActivity extends AppCompatActivity {

    @Extra
    DailyData dailyData;

    @Extra
    LocationData locationData;

    @Bean
    WeatherDrawableProvider weatherDrawableProvider;

    @Bean
    TemperatureConverter temperatureConverter;

    @Bean
    DateFormatter dataFormatter;

    @ViewById
    TextView textViewSummary;

    @ViewById
    TextView textViewDate;

    @ViewById
    TextView textViewTempMin;

    @ViewById
    TextView textViewTimeTempMin;

    @ViewById
    TextView textViewTempMax;

    @ViewById
    TextView textViewTimeTempMax;

    @ViewById
    TextView textViewUvIndex;

    @ViewById
    TextView textViewHumidity;

    @ViewById
    TextView textViewSunrise;

    @ViewById
    TextView textViewSunset;


    @ViewById
    RelativeLayout backgroundLayout;

    @AfterViews
    void init() {
        showData();
        getSupportActionBar().hide();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    private void showData() {
        textViewSummary.setText(dailyData.getSummary());
        textViewTempMax.setText(String.valueOf((temperatureConverter.convertToCelsius(dailyData.getTemperatureMax()))));
        textViewTempMin.setText(String.valueOf((temperatureConverter.convertToCelsius(dailyData.getTemperatureMin()))));
        textViewTimeTempMin.setText(String.valueOf(dataFormatter.toHour(dailyData.getTemperatureMinTime())));
        textViewTimeTempMax.setText(String.valueOf(dataFormatter.toHour(dailyData.getTemperatureMaxTime())));
        textViewDate.setText(String.valueOf(dataFormatter.toDate(dailyData.getTime())));
        textViewUvIndex.setText(String.valueOf(dailyData.getUvIndex()));
        textViewHumidity.setText(String.valueOf(dailyData.getHumidity()));
        backgroundLayout.setBackgroundResource(weatherDrawableProvider.getWeatherBackground(dailyData.getIcon()));
        if (dailyData.getSunriseTime() != null && dailyData.getSunsetTime() != null) {
            textViewSunrise.setText(String.valueOf(dataFormatter.toHour(dailyData.getSunriseTime())));
            textViewSunset.setText(String.valueOf(dataFormatter.toHour(dailyData.getSunsetTime())));
        }
    }
}