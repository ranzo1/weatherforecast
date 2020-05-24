package eu.execom.weatherforecast.repository.local.shared_preferences;

import android.content.Context;

import eu.execom.weatherforecast.usecase.dependency.repository.SettingsDao;

public class SettingsDaoImpl implements SettingsDao {

    private final static String MEASUREMENT_UNIT_KEY = "measureUnit";
    private TinyDB tinyDB;

    public SettingsDaoImpl(Context context) {
        this.tinyDB = new TinyDB(context);
    }

    @Override
    public void setTemperatureUnit(String unit) {
        tinyDB.putString(MEASUREMENT_UNIT_KEY, unit);
    }

    @Override
    public String getTemperatureUnit() {
        return tinyDB.getString(MEASUREMENT_UNIT_KEY);
    }
}
