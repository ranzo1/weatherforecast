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
import eu.execom.weatherforecast.ui.DateFormatter;
import eu.execom.weatherforecast.ui.WeatherDrawableProvider;
import eu.execom.weatherforecast.domain.DailyData;

@EViewGroup(R.layout.daily_weather_item_view)
public class DailyDataItemView extends RelativeLayout {

    @Bean
    DateFormatter dateFormatter;
    @Bean
    WeatherDrawableProvider weatherDrawableProvider;
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
        imageViewWeather.setImageResource(weatherDrawableProvider.getWeatherIcons(dailyData.getIcon()));
        textViewDay.setText(dateFormatter.toDay(dailyData.getTime()));
    }
}
