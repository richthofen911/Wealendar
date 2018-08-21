package net.callofdroidy.wealendar.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class WeatherNow extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
