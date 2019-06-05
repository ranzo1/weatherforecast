package eu.execom.weatherforecast.ui;

import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import eu.execom.weatherforecast.MyApplication;
import eu.execom.weatherforecast.R;
import eu.execom.weatherforecast.domain.Coordinates;
import eu.execom.weatherforecast.domain.Currently;
import eu.execom.weatherforecast.domain.DailyData;
import eu.execom.weatherforecast.domain.DailyWeather;
import eu.execom.weatherforecast.ui.adapter.generic.DailyDataAdapter;
import eu.execom.weatherforecast.usecase.WeatherUseCase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

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

    private CompositeDisposable compositeDisposable;

    @AfterViews
    void init() {
        myApplication.getComponent().inject(this);
        requestPermission();

        getSupportActionBar().hide();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );



        recyclerWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false));
        recyclerWeather.setAdapter(dailyDataAdapter);

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(weatherUseCase.getWeatherForecastForCurrentLocation(new Coordinates(19.833549,45.267136))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DailyWeather>() {
                    @Override
                    public void accept(DailyWeather dailyWeathers) throws Exception {

                        dailyDataAdapter.setItems(dailyWeathers.getDaily().getData());
                        setBackground();
                        setIcon(dailyWeathers.getCurrently());
                        textViewTemperature.setText(convertToCelsius(dailyWeathers.getCurrently()));
                        textViewDescription.setText(dailyWeathers.getCurrently().getSummary());
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(myApplication, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION}, 1);
    }

    private void setBackground(){

        backgroundWeatherLayout.setBackgroundResource(R.drawable.night);

    }

    private void setIcon(Currently currently){
        if(currently.getIcon().toString().equals("clear-day")){

            imageViewWeather.setImageResource(R.drawable.ic_clear_day);

        }else if(currently.getIcon().toString().equals("clear-night")){

            imageViewWeather.setImageResource(R.drawable.ic_clear_night);

        }else if(currently.getIcon().toString().equals("rain")){

            imageViewWeather.setImageResource(R.drawable.ic_rain);

        }else if(currently.getIcon().toString().equals("snow")){

            imageViewWeather.setImageResource(R.drawable.ic_snow);

        }else if(currently.getIcon().toString().equals("sleet")){

            imageViewWeather.setImageResource(R.drawable.ic_sleet);

        }else if(currently.getIcon().toString().equals("wind")){

            imageViewWeather.setImageResource(R.drawable.ic_wind);

        }else if(currently.getIcon().toString().equals("fog")){

            imageViewWeather.setImageResource(R.drawable.ic_fog);

        }else if(currently.getIcon().toString().equals("cloudy")){

            imageViewWeather.setImageResource(R.drawable.ic_cloudy);

        }else if(currently.getIcon().toString().equals("partly-cloudy-day")){

            imageViewWeather.setImageResource(R.drawable.ic_partly_cloudy_day);

        }else if(currently.getIcon().toString().equals("partly-cloudy-night")){

            imageViewWeather.setImageResource(R.drawable.ic_partly_cloudy_night);

        }

    }

    private String convertToCelsius(Currently currently){

        float value = ((currently.getTemperature() - 32)*5)/9;
        String celsius = String.valueOf(value);

        if(Integer.valueOf(celsius.substring(3,5))>=5){
            int temp =Integer.valueOf(celsius.substring(0,2));
            celsius=String.valueOf(temp);
        }else{

            celsius=celsius.substring(0,2);
        }


        return celsius;
    }



}
