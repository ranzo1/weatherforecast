package eu.execom.weatherforecast.ui;

import android.annotation.SuppressLint;

import org.androidannotations.annotations.EBean;

import java.text.SimpleDateFormat;
import java.util.Date;

@EBean
public class SimpleDataFormater {

    @SuppressLint("SimpleDateFormat")
    public String dateFormatToDay(Date date) {

        return new SimpleDateFormat("E").format(date);
    }
}
