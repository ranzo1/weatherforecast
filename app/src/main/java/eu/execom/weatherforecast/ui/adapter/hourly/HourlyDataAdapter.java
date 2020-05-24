package eu.execom.weatherforecast.ui.adapter.hourly;

import android.content.Context;

import androidx.annotation.NonNull;

import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import eu.execom.weatherforecast.domain.HourlyData;
import eu.execom.weatherforecast.ui.adapter.generic.RecyclerViewAdapterBase;
import eu.execom.weatherforecast.ui.adapter.generic.ViewWrapper;

@EBean
public class HourlyDataAdapter extends RecyclerViewAdapterBase<HourlyData, HourlyDataItemView> {

    @RootContext
    Context context;

    private String temperatureUnit;

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<HourlyDataItemView> holder, int position) {
        holder.getView().bind(items.get(position),temperatureUnit);
    }

    @Override
    protected HourlyDataItemView onCreateItemView(ViewGroup parent, int viewType) {
        return HourlyDataItemView_.build(context);
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }
}
