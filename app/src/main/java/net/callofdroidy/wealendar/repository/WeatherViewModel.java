package net.callofdroidy.wealendar.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import net.callofdroidy.wealendar.R;
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


    private LiveData<List<Weather>> weathers;

    public WeatherViewModel() {

    }

    public LiveData<List<Weather>> getWeathers(Context context) {
        if (weathers == null) {
            weathers = new MutableLiveData<List<Weather>>();
            loadWeathers(707860, context);
        }

        return weathers;
    }

    private void loadWeathers(long cityId, Context context) {
        // async operation here
        compositeDisposable.add(NetworkService.getInstance()
                .get5DayForecast(cityId, context.getString(R.string.open_weather_api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    List<WeatherData> weatherData = response.getList();
                    List<Weather> dataToSave = new ArrayList<>();
                    for (WeatherData data : weatherData) {
                        dataToSave.add(new Weather(response.getCity().getName(), data.getDt(), data.getMain().getTemp()));
                    }

                    Observable.fromCallable(new Callable<long[]>() {
                        @Override
                        public long[] call() throws Exception {
                            return AppDatabase.getAppDatabase(context).weatherDao().insertAll(dataToSave);
                        }
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            //weathers = AppDatabase.getAppDatabase(context).weatherDao().getAll();
                        }
                    });
                    //AppDatabase.getAppDatabase(context).weatherDao().insertAll(dataToSave);

                }, new BaseErrorHandler()));
    }
}
