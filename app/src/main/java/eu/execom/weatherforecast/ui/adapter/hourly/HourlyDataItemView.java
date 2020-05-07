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
import eu.execom.weatherforecast.ui.WeatherPercentageFormatter;

@EViewGroup(R.layout.hourly_weather_item_view)
public class HourlyDataItemView extends RelativeLayout {

    @Bean
    DateFormatter dateFormatter;

    @Bean
    WeatherDrawableProvider weatherDrawableProvider;

    @Bean
    TemperatureConverter temperatureConverter;

    @Bean
    WeatherPercentageFormatter percentageFormatter;

    @ViewById
    TextView textViewHour;

    @ViewById
    ImageView imageViewWeather;

    @ViewById
    TextView temperatureHourly;

    @ViewById
    ImageView temperatureHourlyIcon;

    @ViewById
    TextView precipProbabilityTextView;

    @ViewById
    ImageView precipProbabilityImage;

    public HourlyDataItemView(Context context) {
        super(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = context.getResources().getDimensionPixelSize(R.dimen.item_weather_margin);
        layoutParams.setMargins(margin, margin, margin, margin);
        setLayoutParams(layoutParams);
        setClickable(true);
    }

    public void bind(HourlyData hourlyData) {
        String temperatureCelsius = temperatureConverter.convertToCelsius(hourlyData.getTemperature()) + "°";
        int temperature = temperatureConverter.convertToCelsius(hourlyData.getTemperature());
        int precipProbability = percentageFormatter.getPercentage(hourlyData.getPrecipProbability());
        String precipProbabilityPercent = precipProbability + "%";

        imageViewWeather.setImageResource(weatherDrawableProvider.getWeatherIcons(hourlyData.getIcon()));
        textViewHour.setText(dateFormatter.toHour(hourlyData.getTime()));
        temperatureHourly.setText(temperatureCelsius);
        temperatureHourlyIcon.setImageResource(weatherDrawableProvider.getTemperatureImage(temperature));
        precipProbabilityTextView.setText(precipProbabilityPercent);
        precipProbabilityImage.setImageResource(weatherDrawableProvider.getPrecipProbabilityImage(precipProbability));
    }
}
