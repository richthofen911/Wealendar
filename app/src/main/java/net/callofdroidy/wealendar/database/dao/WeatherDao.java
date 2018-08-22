package net.callofdroidy.wealendar.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import net.callofdroidy.wealendar.database.entity.Weather;

import java.util.List;

@Dao
public interface WeatherDao {
    @Query("SELECT * FROM Weather")
    LiveData<List<Weather>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Weather... weathers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<Weather> weathers);

    @Delete
    void delete(Weather weather);
}
