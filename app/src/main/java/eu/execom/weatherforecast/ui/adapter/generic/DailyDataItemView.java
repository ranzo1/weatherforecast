package eu.execom.weatherforecast.ui.adapter.generic;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import eu.execom.weatherforecast.R;
import eu.execom.weatherforecast.SimpleDataFormater;
import eu.execom.weatherforecast.WeatherIconProvider;
import eu.execom.weatherforecast.domain.DailyData;

@EViewGroup(R.layout.daily_weather_item_view)
public class DailyDataItemView extends RelativeLayout {

    @Bean
    SimpleDataFormater simpleDataFormater;
    @Bean
    WeatherIconProvider weatherIconProvider;
    @ViewById
    TextView textViewDay;
    @ViewById
    ImageView imageViewWeather;

    public DailyDataItemView(Context context) {
        super(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = context.getResources().getDimensionPixelSize(R.dimen.item_weather_margin);
        layoutParams.setMargins(margin, margin, margin, margin);
        setLayoutParams(layoutParams);
    }

    public void bind(DailyData dailyData) {
        imageViewWeather.setImageResource(weatherIconProvider.getWeatherIcon(dailyData.getIcon()));
        textViewDay.setText(simpleDataFormater.dateFormatToDay(dailyData.getTime()));
    }
}
