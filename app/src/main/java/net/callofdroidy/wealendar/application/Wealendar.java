package net.callofdroidy.wealendar.application;

import com.facebook.stetho.Stetho;

import net.callofdroidy.core.BuildConfig;
import net.callofdroidy.core.application.BaseApplication;

public class Wealendar extends BaseApplication {
    private static Wealendar INSTANCE;

    public synchronized static Wealendar get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

    }
}
