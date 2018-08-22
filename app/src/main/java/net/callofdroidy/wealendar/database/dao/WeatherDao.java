package net.callofdroidy.wealendar.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import net.callofdroidy.wealendar.database.entity.Weather;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM Weather")
    LiveData<List<Weather>> getAll();

    @Insert
    void insertAll(Weather... weathers);

    @Insert
    long[] insertAll(List<Weather> weathers);

    @Delete
    void delete(Weather weather);
}
