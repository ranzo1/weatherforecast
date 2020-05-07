package eu.execom.weatherforecast.ui.adapter.daily;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import eu.execom.weatherforecast.R;
import eu.execom.weatherforecast.TemperatureConverter;
import eu.execom.weatherforecast.domain.DailyData;
import eu.execom.weatherforecast.ui.DateFormatter;
import eu.execom.weatherforecast.ui.WeatherDrawableProvider;

@EViewGroup(R.layout.daily_weather_item_view)
public class DailyDataItemView extends RelativeLayout {

    @Bean
    DateFormatter dateFormatter;

    @Bean
    WeatherDrawableProvider weatherDrawableProvider;

    @Bean
    TemperatureConverter temperatureConverter;

    @ViewById
    TextView textViewDay;

    @ViewById
    TextView temperatureHighest;

    @ViewById
    TextView temperatureLowest;

    @ViewById
    ImageView imageViewWeather;

    @ViewById
    ImageView imageViewMaxTemperature;

    public DailyDataItemView(Context context) {
        super(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = context.getResources().getDimensionPixelSize(R.dimen.item_weather_margin);
        layoutParams.setMargins(margin, margin, margin, margin);
        setLayoutParams(layoutParams);
        setClickable(true);
    }

    public void bind(DailyData dailyData, DailyDataItemActionListener listener) {
        String maxTemperatureCelsius = temperatureConverter.convertToCelsius(dailyData.getTemperatureMax()) + "°";
        String minTemperatureCelsius = temperatureConverter.convertToCelsius(dailyData.getTemperatureMin()) + "°";
        int maxTemperature = temperatureConverter.convertToCelsius(dailyData.getTemperatureMax());
        imageViewWeather.setImageResource(weatherDrawableProvider.getWeatherIcons(dailyData.getIcon()));
        imageViewMaxTemperature.setImageResource(weatherDrawableProvider.getMaxTemperatureImage(maxTemperature));
        temperatureHighest.setText(maxTemperatureCelsius);
        temperatureLowest.setText(minTemperatureCelsius);
        textViewDay.setText(dateFormatter.toDay(dailyData.getTime()));
        setOnClickListener(view -> listener.onItemClick(dailyData));
    }

    public interface DailyDataItemActionListener {
        void onItemClick(DailyData dailyData);
    }
}