package eu.execom.weatherforecast.ui.adapter.daily;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import eu.execom.weatherforecast.R;
import eu.execom.weatherforecast.UnitsConverter;
import eu.execom.weatherforecast.domain.DailyData;
import eu.execom.weatherforecast.ui.DateFormatter;
import eu.execom.weatherforecast.ui.WeatherDrawableProvider;

@EViewGroup(R.layout.daily_weather_item_view)
public class DailyDataItemView extends RelativeLayout {

    private static final String FAHRENHEIT = "fahrenheit";
    private static final String CELSIUS = "celsius";

    @Bean
    DateFormatter dateFormatter;

    @Bean
    WeatherDrawableProvider weatherDrawableProvider;

    @Bean
    UnitsConverter temperatureConverter;

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

    public void bind(DailyData dailyData, DailyDataItemActionListener listener, String temperatureUnit) {
        int maxTemperature = 0;
        int minTemperature = 0;

        if (temperatureUnit.equals(FAHRENHEIT)) {
            maxTemperature = Math.round(dailyData.getTemperatureMax());
            minTemperature = Math.round(dailyData.getTemperatureMin());
            imageViewMaxTemperature.setImageResource(weatherDrawableProvider.getMaxTemperatureInFahrenheitImage(maxTemperature));
        }
        if (temperatureUnit.equals(CELSIUS)) {
            maxTemperature = temperatureConverter.convertToCelsius(dailyData.getTemperatureMax());
            minTemperature = temperatureConverter.convertToCelsius(dailyData.getTemperatureMin());
            imageViewMaxTemperature.setImageResource(weatherDrawableProvider.getMaxTemperatureInCelsiusImage(maxTemperature));
        }

        String maxTemperatureConverted = maxTemperature + "°";
        String minTemperatureConverted = minTemperature + "°";

        imageViewWeather.setImageResource(weatherDrawableProvider.getWeatherIcons(dailyData.getIcon()));
        temperatureHighest.setText(maxTemperatureConverted);
        temperatureLowest.setText(minTemperatureConverted);
        textViewDay.setText(dateFormatter.toDay(dailyData.getTime()));
        setOnClickListener(view -> listener.onItemClick(dailyData));
    }

    public interface DailyDataItemActionListener {
        void onItemClick(DailyData dailyData);
    }
}