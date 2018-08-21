package net.callofdroidy.wealendar.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Weather {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "date")
    public long date;

    @ColumnInfo(name = "temp")
    public float temp;

    public Weather(String city, long date, float temp) {
        this.city = city;
        this.date = date;
        this.temp = temp;
    }
}
