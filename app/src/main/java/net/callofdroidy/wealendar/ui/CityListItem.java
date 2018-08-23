package net.callofdroidy.wealendar.ui;

import android.graphics.drawable.Drawable;

public class CityListItem extends BaseItem {
    private String cityName;
    private String temperature;
    private String shortDescription;
    private Drawable icon;

    public CityListItem(String cityName, String temperature, String shortDescription, Drawable icon) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.shortDescription = shortDescription;
        this.icon = icon;
    }

    public String getCityName() {
        return cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Drawable getIconId() {
        return icon;
    }
}
