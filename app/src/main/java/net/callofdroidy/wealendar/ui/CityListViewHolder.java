package net.callofdroidy.wealendar.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CityListViewHolder extends BaseViewHolder {
    private TextView tvCityName;
    private TextView tvTemperature;
    private ImageView ivWeatherIcon;
    private TextView tvShortDescription;

    public CityListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(BaseItem item) {
        if (item instanceof CityListItem) {
            CityListItem cityListItem = (CityListItem)item;
            tvCityName.setText(cityListItem.getCityName());
            tvTemperature.setText(cityListItem.getTemperature());
            ivWeatherIcon.setImageDrawable(cityListItem.getIconId());
        }
    }
}
