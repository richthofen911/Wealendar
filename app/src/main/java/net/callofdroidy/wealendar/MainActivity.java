package net.callofdroidy.wealendar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import net.callofdroidy.wealendar.database.entity.Weather;
import net.callofdroidy.wealendar.network.jsonmodel.WeatherForecastResponse;
import net.callofdroidy.wealendar.repository.WeatherViewModel;
import net.callofdroidy.wealendar.database.AppDatabase;
import net.callofdroidy.wealendar.network.jsonmodel.WeatherData;
import net.callofdroidy.wealendar.network.service.NetworkService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private CompositeDisposable compositeDisposable =
            new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherViewModel model = ViewModelProviders.of(this).get(WeatherViewModel.class);
        model.getWeathers(this).observe(this, new Observer<List<Weather>>() {
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
        compositeDisposable.clear();
    }
}
