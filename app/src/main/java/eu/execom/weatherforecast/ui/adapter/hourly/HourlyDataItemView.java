package eu.execom.weatherforecast.ui.adapter.hourly;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import eu.execom.weatherforecast.R;
import eu.execom.weatherforecast.TemperatureConverter;
import eu.execom.weatherforecast.domain.HourlyData;
import eu.execom.weatherforecast.ui.DateFormatter;
import eu.execom.weatherforecast.ui.WeatherDrawableProvider;

@EViewGroup(R.layout.hourly_weather_item_view)
public class HourlyDataItemView extends RelativeLayout {

    @Bean
    DateFormatter dateFormatter;

    @Bean
    WeatherDrawableProvider weatherDrawableProvider;

    @Bean
    TemperatureConverter temperatureConverter;

    @ViewById
    TextView textViewHour;

    @ViewById
    ImageView imageViewWeather;

    @ViewById
    TextView temperatureHourly;

    @ViewById
    ImageView temperatureHourlyIcon;

    public HourlyDataItemView(Context context) {
        super(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = context.getResources().getDimensionPixelSize(R.dimen.item_weather_margin);
        layoutParams.setMargins(margin, margin, margin, margin);
        setLayoutParams(layoutParams);
        setClickable(true);
    }

    public void bind(HourlyData hourlyData) {
        imageViewWeather.setImageResource(weatherDrawableProvider.getWeatherIconsMonoColor(hourlyData.getIcon()));
        textViewHour.setText(dateFormatter.toHour(hourlyData.getTime()));
        temperatureHourly.setText(String.valueOf(temperatureConverter.convertToCelsius(hourlyData.getTemperature())));
        temperatureHourlyIcon.setImageResource(weatherDrawableProvider.getTemperatureImage(temperatureConverter.convertToCelsius(hourlyData.getTemperature())));
    }
}
