package net.callofdroidy.wealendar.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import net.callofdroidy.wealendar.R;

import java.util.List;

public class CityListRvAdapter extends RecyclerView.Adapter<CityListViewHolder> {

    @NonNull
    private List<CityListItem> cityListItems;

    public CityListRvAdapter(@NonNull List<CityListItem> cityListItems) {
        this.cityListItems = cityListItems;
    }

    @NonNull
    @Override
    public CityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CityListViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.city_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CityListViewHolder holder, int position) {
        holder.onBind(cityListItems.get(position));
    }

    @Override
    public int getItemCount() {
        return cityListItems.size();
    }
}
