package eu.execom.weatherforecast.ui.adapter.generic;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private final V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}
