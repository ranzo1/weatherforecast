package eu.execom.weatherforecast.usecase.dependency.repository;

public interface SettingsDao {

    void setTemperatureUnit(String unit);

    String getTemperatureUnit();
}
