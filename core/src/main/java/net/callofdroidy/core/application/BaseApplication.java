package net.callofdroidy.core.application;

import android.app.Application;

public class BaseApplication extends Application {
    private static BaseApplication INSTANCE;

    public synchronized static BaseApplication get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
    }
}
