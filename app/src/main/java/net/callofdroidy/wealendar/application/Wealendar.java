package net.callofdroidy.wealendar.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class Wealendar extends Application {
    private static Wealendar INSTANCE;

    public synchronized static Wealendar get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        Stetho.initializeWithDefaults(this);
    }
}
