package eu.execom.weatherforecast.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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

    private DailyDataItemView.DailyDataItemActionListener dailyDataItemActionListener;

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<DailyDataItemView> holder, int position) {
        holder.getView().bind(items.get(position), dailyDataItemActionListener);
    }

    @Override
    protected DailyDataItemView onCreateItemView(ViewGroup parent, int viewType) {
        return DailyDataItemView_.build(context);
    }

    public void setDailyDataItemActionListener(DailyDataItemView.DailyDataItemActionListener dailyDataItemActionListener) {
        this.dailyDataItemActionListener = dailyDataItemActionListener;
    }
}
