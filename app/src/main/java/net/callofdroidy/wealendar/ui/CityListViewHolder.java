package net.callofdroidy.wealendar.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.callofdroidy.wealendar.R;

import java.util.Locale;

public class CityListViewHolder extends BaseViewHolder {
    private TextView tvCityName;
    private TextView tvTemperature;
    private ImageView ivWeatherIcon;
    private TextView tvShortDescription;

    CityListViewHolder(View itemView) {
        super(itemView);

        tvCityName = itemView.findViewById(R.id.tv_city_list_city_name);
        tvTemperature = itemView.findViewById(R.id.tv_city_list_temperature);
        ivWeatherIcon = itemView.findViewById(R.id.iv_city_list_weather_icon);
        tvShortDescription = itemView.findViewById(R.id.tv_city_list_short_description);
    }

    @Override
    protected void onBind(BaseItem item) {
        if (item instanceof CityListItem) {
            CityListItem cityListItem = (CityListItem)item;
            tvCityName.setText(cityListItem.getCityName());
            tvTemperature.setText(String.format(Locale.CANADA, "%s \u2103", cityListItem.getTemperature()));
            ivWeatherIcon.setImageDrawable(cityListItem.getIconId());
            tvShortDescription.setText(cityListItem.getShortDescription());
        }
    }
}
