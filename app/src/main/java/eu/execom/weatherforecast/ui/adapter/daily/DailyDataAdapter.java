package eu.execom.weatherforecast.ui.adapter.daily;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import eu.execom.weatherforecast.domain.DailyData;
import eu.execom.weatherforecast.ui.adapter.generic.RecyclerViewAdapterBase;
import eu.execom.weatherforecast.ui.adapter.generic.ViewWrapper;

@EBean
public class DailyDataAdapter extends RecyclerViewAdapterBase<DailyData, DailyDataItemView> {

    @RootContext
    Context context;

    private String temperatureUnit;

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<DailyDataItemView> holder, int position) {
        holder.getView().bind(items.get(position), temperatureUnit);
    }

    @Override
    protected DailyDataItemView onCreateItemView(ViewGroup parent, int viewType) {
        return DailyDataItemView_.build(context);
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }
}
