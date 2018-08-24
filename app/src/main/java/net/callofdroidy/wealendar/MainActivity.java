package net.callofdroidy.wealendar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.callofdroidy.wealendar.database.entity.Weather;
import net.callofdroidy.wealendar.repository.WeatherViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherViewModel model = ViewModelProviders.of(this).get(WeatherViewModel.class);
        model.fetchWeatherByCity("707860");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                model.getAllWeathers().observe(MainActivity.this, new Observer<List<Weather>>() {
                    @Override
                    public void onChanged(@Nullable List<Weather> weathers) {
                        if (weathers != null) {
                            Log.e(TAG, "onChanged: " + weathers.get(0).temp);
                        } else {
                            Log.e(TAG, "onChanged: weathers null");
                        }
                    }
                });
            }
        }, 2000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
