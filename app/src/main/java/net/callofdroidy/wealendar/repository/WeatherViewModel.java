package net.callofdroidy.wealendar.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import net.callofdroidy.wealendar.database.AppDatabase;
import net.callofdroidy.wealendar.database.entity.Weather;
import net.callofdroidy.wealendar.network.errorhandler.BaseErrorHandler;
import net.callofdroidy.wealendar.network.jsonmodel.WeatherData;
import net.callofdroidy.wealendar.network.service.NetworkService;
import net.callofdroidy.wealendar.network.service.OpenWeatherService;
import net.callofdroidy.weathernow.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends BaseViewModel {
    private OpenWeatherService openWeatherService;

    private MutableLiveData<List<Weather>> weathers;

    public WeatherViewModel(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }

    public LiveData<List<Weather>> getWeathers() {
        if (weathers == null) {
            weathers = new MutableLiveData<List<Weather>>();
            loadWeathers();
        }

        return weathers;
    }

    private void loadWeathers(long cityId, Context context) {
        // async operation here
        compositeDisposable.add(NetworkService.getInstance()
                .get5DayForecast(707860, context.getString(R.string.open_weather_api_key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    List<WeatherData> weatherData = response.getList();
                    List<Weather> dataToSave = new ArrayList<>();
                    for (WeatherData data : weatherData) {
                        dataToSave.add(new Weather(response.getCity().getName(), data.getDt(), data.getMain().getTemp()));
                    }
                    AppDatabase.getAppDatabase(context).weatherDao().insertAll(dataToSave);
                }, new BaseErrorHandler()));
    }
}
