package net.callofdroidy.wealendar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.gson.Gson;

import net.callofdroidy.core.utils.FileManager;
import net.callofdroidy.wealendar.KEY;
import net.callofdroidy.wealendar.MainActivity;
import net.callofdroidy.wealendar.R;
import net.callofdroidy.wealendar.database.AppDatabase;
import net.callofdroidy.wealendar.database.entity.Weather;
import net.callofdroidy.wealendar.network.errorhandler.BaseErrorHandler;
import net.callofdroidy.wealendar.network.jsonmodel.City;
import net.callofdroidy.wealendar.network.jsonmodel.SingleWeatherCurrentResponse;
import net.callofdroidy.wealendar.network.service.NetworkService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreen";

    private static final long UPDATE_INTERVAL = 15 * 60 * 1000; // 15 min

    private View mContentView;

    private boolean isInitializationDone = false;

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

        new Handler().postDelayed(() -> {
            if (isInitializationDone) {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);

        long lastUpdateTime = FileManager.get()
                .getSharedPreference(KEY.FILE_APP_INFO)
                .getLong(KEY.DATA_LAST_UPDATE_TIME, 0);

        if (System.currentTimeMillis() - lastUpdateTime >= UPDATE_INTERVAL) {
            Gson gson = new Gson();
            City[] cities = gson.fromJson(
                    FileManager.get().loadJsonFromAsset(KEY.FILE_CITY_JSON), City[].class);
            StringBuilder stringBuilder = new StringBuilder();

            for (City city : cities) {
                stringBuilder.append(city.getId()).append(",");
            }

            try {
                String encoded = URLEncoder.encode(
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString(), "utf-8");
                CompositeDisposable compositeDisposable = new CompositeDisposable();
                compositeDisposable.add(NetworkService.getInstance()
                        .getMultipleCitiesCurrentWeather(
                                encoded)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            List<Weather> weathers = new ArrayList<>();
                            for(SingleWeatherCurrentResponse singleWeather : response.getList()) {
                                weathers.add(new Weather(
                                        singleWeather.getDt(),
                                        singleWeather.getId(),
                                        singleWeather.getName(),
                                        singleWeather.getMain().getTemp(),
                                        singleWeather.getWeather().get(0).getId(),
                                        singleWeather.getWeather().get(0).getDescription()
                                ));
                            }
                            compositeDisposable.add(
                                    Observable.fromCallable(
                                            () -> AppDatabase.get().weatherDao()
                                                    .insert(weathers)).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe((Consumer<Object>) o -> {
                                                FileManager.get()
                                                        .getSharedPreference(KEY.FILE_APP_INFO)
                                                        .edit()
                                                        .putLong(
                                                                KEY.DATA_LAST_UPDATE_TIME,
                                                                System.currentTimeMillis()).apply();

                                                isInitializationDone = true;
                                            }, new BaseErrorHandler())
                            );

                        }, new BaseErrorHandler())
                );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            isInitializationDone = true;
        }
    }
}
