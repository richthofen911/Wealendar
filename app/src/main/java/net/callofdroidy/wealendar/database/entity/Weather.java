package net.callofdroidy.wealendar.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Weather {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "date")
    public long date;

    @ColumnInfo(name = "city_id")
    public long cityId;

    @ColumnInfo(name = "city_name")
    public String cityName;

    @ColumnInfo(name = "temperature")
    public float temperature;

    @ColumnInfo(name = "weather_condition_id")
    public int weatherConditionId;

    @ColumnInfo(name = "weather_description")
    public String weatherDescription;

    public Weather(long date, long cityId, String cityName,
                   float temperature, int weatherConditionId, String weatherDescription) {
        this.date = date;
        this.cityId = cityId;
        this.cityName = cityName;
        this.temperature = temperature;
        this.weatherConditionId = weatherConditionId;
        this.weatherDescription = weatherDescription;
    }
}
