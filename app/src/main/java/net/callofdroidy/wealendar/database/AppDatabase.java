package net.callofdroidy.wealendar.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import net.callofdroidy.wealendar.R;
import net.callofdroidy.wealendar.application.Wealendar;
import net.callofdroidy.wealendar.database.dao.WeatherDao;
import net.callofdroidy.wealendar.database.entity.Weather;

@Database(entities = {Weather.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public static AppDatabase get() {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(Wealendar.get(), AppDatabase.class,
                            Wealendar.get().getString(R.string.database_name))
                            // allow queries on the main thread.
                            //.allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public abstract WeatherDao weatherDao();

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
