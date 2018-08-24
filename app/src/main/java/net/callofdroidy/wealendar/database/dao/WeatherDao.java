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

    @Query("SELECT * FROM Weather ORDER BY date ASC LIMIT :cityInTotal")
    LiveData<List<Weather>> getCurrentWeatherOfCities(int cityInTotal);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Weather... weathers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(List<Weather> weathers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Weather weather);

    @Delete
    void delete(Weather weather);

    @Query("DELETE FROM Weather WHERE date < date('now')")
    void deleteExpiredWeather();
}
