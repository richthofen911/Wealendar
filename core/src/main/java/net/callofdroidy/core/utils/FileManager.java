package net.callofdroidy.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import net.callofdroidy.core.application.BaseApplication;

import java.io.IOException;
import java.io.InputStream;

import javax.security.auth.login.LoginException;

public class FileManager {
    private static FileManager INSTANCE;

    private Context context;

    public synchronized static FileManager get() {
        if (INSTANCE == null) {
            INSTANCE = new FileManager(BaseApplication.get());
        }
        return INSTANCE;
    }

    private FileManager(@NonNull Context context) {
        this.context = context;
    }

    public String loadJsonFromAsset(String fileName) {
        String result;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            result = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return result;
    }

    public SharedPreferences getSharedPreference(String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
}
