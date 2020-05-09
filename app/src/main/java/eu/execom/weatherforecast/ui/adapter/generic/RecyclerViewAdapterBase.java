package eu.execom.weatherforecast.ui.adapter.generic;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected List<T> items = new ArrayList<>();

    @Override
    public ViewWrapper<V> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
