package net.callofdroidy.wealendar.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import net.callofdroidy.wealendar.R;
import net.callofdroidy.wealendar.application.Wealendar;
import net.callofdroidy.wealendar.database.AppDatabase;
import net.callofdroidy.wealendar.database.entity.Weather;
import net.callofdroidy.wealendar.network.errorhandler.BaseErrorHandler;
import net.callofdroidy.wealendar.network.jsonmodel.WeatherData;
import net.callofdroidy.wealendar.network.service.NetworkService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends BaseViewModel {
    private static final String TAG = "WeatherViewModel";

    private MutableLiveData<List<Weather>> weathers;

    public WeatherViewModel() {
        weathers = new MutableLiveData<>();
    }

    public LiveData<List<Weather>> getWeathersByCity(long cityId) {
        loadWeathers(cityId);

        return weathers;
    }

    public LiveData<List<Weather>> getWeathersByDate(long date) {
        return null;
    }

    private void loadWeathers(long cityId) {
        // async operation here
        compositeDisposable.add(NetworkService.getInstance()
                .get5DayForecast(cityId, Wealendar.get().getString(R.string.open_weather_api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    List<WeatherData> weatherData = response.getList();
                    List<Weather> dataToSave = new ArrayList<>();
                    for (WeatherData data : weatherData) {
                        dataToSave.add(new Weather(
                                response.getCity().getName(), data.getDt(), data.getMain().getTemp()));
                    }

                    Observable.fromCallable(new Callable<long[]>() {
                        @Override
                        public long[] call() throws Exception {
                            return AppDatabase.get().weatherDao().insertAll(dataToSave);
                        }
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            LiveData<List<Weather>> result = AppDatabase.get().weatherDao().getAll();
                            Log.e(TAG, "accept: " + result.getValue().get(0).city);
                            //weathers.postValue(result);
                        }
                    });
                }, new BaseErrorHandler()));
    }
}
