package eu.execom.weatherforecast;

import org.androidannotations.annotations.EBean;

import java.text.SimpleDateFormat;
import java.util.Date;

@EBean
public class SimpleDataFormater {

    public String dateFormatToDay(Date date) {

        return new SimpleDateFormat("E").format(date);
    }
}
