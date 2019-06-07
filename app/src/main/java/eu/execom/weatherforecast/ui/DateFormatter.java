package eu.execom.weatherforecast.ui;

import android.annotation.SuppressLint;

import org.androidannotations.annotations.EBean;

import java.text.SimpleDateFormat;
import java.util.Date;

@EBean
public class DateFormatter {

    @SuppressLint("SimpleDateFormat")
    public String toDay(Date date) {

        return new SimpleDateFormat("E").format(date);
    }
}
