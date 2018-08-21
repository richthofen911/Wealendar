package net.callofdroidy.wealendar;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import net.callofdroidy.wealendar.database.entity.Weather;
import net.callofdroidy.wealendar.network.jsonmodel.WeatherForecastResponse;
import net.callofdroidy.weathernow.R;
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

        ApplicationInfo ai = null;
        try {
            ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            String openWeatherAppId = ai.metaData.getString("open_weather_api_key", null);

            Disposable disposable = NetworkService.getInstance()
                    .get5DayForecast(707860, openWeatherAppId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<WeatherForecastResponse>() {
                        @Override
                        public void accept(WeatherForecastResponse response) throws Exception {
                            List<WeatherData> weatherData = response.getList();
                            List<Weather> dataToSave = new ArrayList<>();
                            for (WeatherData data : weatherData) {
                                dataToSave.add(new Weather(response.getCity().getName(), data.getDt(), data.getMain().getTemp()));
                            }
                            AppDatabase.getAppDatabase(MainActivity.this).weatherDao().insertAll(dataToSave);
                        }
                    }, new ForecastErrorConsumer());
            compositeDisposable.add(disposable);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    private class ForecastConsumer implements Consumer<WeatherForecastResponse> {
        @Override
        public void accept(WeatherForecastResponse response) throws Exception {
            List<WeatherData> weatherData = response.getList();
            List<Weather> dataToSave = new ArrayList<>();
            for (WeatherData data : weatherData) {
                dataToSave.add(new Weather(response.getCity().getName(), data.getDt(), data.getMain().getTemp()));
            }
            AppDatabase.getAppDatabase(MainActivity.this).weatherDao().insertAll(dataToSave);
        }
    }
    */

    private class ForecastErrorConsumer implements Consumer<Throwable> {

        @Override
        public void accept(Throwable throwable) throws Exception {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
        compositeDisposable.clear();
    }
}
