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

    @SuppressLint("SimpleDateFormat")
    public String toHour(Date date) {
        return new SimpleDateFormat("h:mm a").format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public String toDate(Date date) {
        return new SimpleDateFormat("EEE, d MMM yyyy").format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public String toDateAndTime(Date date) {
        return new SimpleDateFormat("EEE, d MMM yyyy HH:mm").format(date);
    }
}