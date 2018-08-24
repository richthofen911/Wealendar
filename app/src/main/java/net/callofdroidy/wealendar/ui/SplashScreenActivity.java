package net.callofdroidy.wealendar.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.gson.Gson;

import net.callofdroidy.core.utils.FileManager;
import net.callofdroidy.wealendar.R;
import net.callofdroidy.wealendar.database.entity.Weather;
import net.callofdroidy.wealendar.network.jsonmodel.City;
import net.callofdroidy.wealendar.repository.WeatherViewModel;

import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreen";

    private static final long UPDATE_INTERVAL = 15 * 60 * 1000; // 15 min

    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        mContentView = findViewById(R.id.fullscreen_content);

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        Gson gson = new Gson();
        City[] cities = gson.fromJson(
                FileManager.get().loadJsonFromAsset("city.json"), City[].class);
        StringBuilder stringBuilder = new StringBuilder();
        for (City city : cities) {
            stringBuilder.append(city.getId()).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();

        WeatherViewModel model = ViewModelProviders.of(this).get(WeatherViewModel.class);
        model.fetchWeatherByCity("707860");
        model.getAllWeathers().observe(SplashScreenActivity.this, new Observer<List<Weather>>() {
            @Override
            public void onChanged(@Nullable List<Weather> weathers) {
                if (weathers != null) {
                    Log.e(TAG, "onChanged: " + weathers.get(0).temp);
                } else {
                    Log.e(TAG, "onChanged: weathers null");
                }
            }
        });

        long lastUpdateTime = FileManager.get().getSharedPreference("app_info").
                getLong("last_update_time", 0);
        if (System.currentTimeMillis() - lastUpdateTime >= UPDATE_INTERVAL) {
            // fetch data from server

        } else {
            // read data from db
        }
    }
}
