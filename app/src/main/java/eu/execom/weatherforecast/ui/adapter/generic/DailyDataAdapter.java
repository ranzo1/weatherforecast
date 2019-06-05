package eu.execom.weatherforecast.ui.adapter.generic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import eu.execom.weatherforecast.domain.DailyData;
import eu.execom.weatherforecast.domain.DailyWeather;

@EBean
public class DailyDataAdapter extends RecyclerViewAdapterBase<DailyData, DailyDataItemView> {

    @RootContext
    Context context;

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<DailyDataItemView> holder, int position) {
        holder.getView().bind(items.get(position));
    }

    @Override
    protected DailyDataItemView onCreateItemView(ViewGroup parent, int viewType) {
        return DailyDataItemView_.build(context);
    }


}
