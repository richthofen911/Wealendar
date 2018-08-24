package net.callofdroidy.wealendar.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import net.callofdroidy.wealendar.database.AppDatabase;
import net.callofdroidy.wealendar.database.entity.Weather;
import net.callofdroidy.wealendar.network.jsonmodel.MultipleWeatherCurrentResponse;
import net.callofdroidy.wealendar.network.jsonmodel.SingleWeatherCurrentResponse;
import net.callofdroidy.wealendar.network.jsonmodel.WeatherForecast;
import net.callofdroidy.wealendar.network.jsonmodel.WeatherForecastResponse;
import net.callofdroidy.wealendar.network.service.NetworkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends BaseViewModel {
    private static final String TAG = "WeatherViewModel";

    private LiveData<List<Weather>> weathers;
    private LiveData<List<Weather>> currentWeathers;

    // 2xx Thunderstorm
    // 3xx Drizzle
    // 5xx Rain
    // 6xx Snow
    // 7xx Mist
    // 800 Clear
    // 80x Clouds

    public WeatherViewModel(Application application) {
        super(application);
        weathers = new MutableLiveData<>();
    }

    public LiveData<List<Weather>> getWeathersByCity(long cityId) {
        return weathers;
    }

    public LiveData<List<Weather>> getAllWeathers() {
        return AppDatabase.get().weatherDao().getAll();
    }

    public LiveData<List<Weather>> getWeathersByDate(long date) {
        return null;
    }

    public void fetchMultipleCitiesCurrentWeathers(String cityIds) {
        compositeDisposable.add(NetworkService.getInstance()
                .getCurrentWeather(cityIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MultipleWeatherCurrentResponse>() {
                    @Override
                    public void accept(MultipleWeatherCurrentResponse response) throws Exception {
                        for(SingleWeatherCurrentResponse singleWeather : response.getList()) {
                            long date = singleWeather.getDt();
                            long cityId = singleWeather.getId();
                            String cityName = singleWeather.getName();

                            float temperature = singleWeather.getMain().getTemp();

                            int weaterConditionId = singleWeather.getWeather().getId();
                            String weatherDescription = singleWeather.getWeather().getDescription();
                        }
                    }
                }, new Consumer<Throwable>() {

                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })
        );
    }

    public void fetchWeatherByCity(String cityId) {
        compositeDisposable.add(NetworkService.getInstance()
                .get5DayForecast(cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResponse>() {
                    @Override
                    public void accept(WeatherForecastResponse weatherForecastResponse) throws Exception {
                        insertWeatherToDB(weatherForecastResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                })
        );
    }

    public void insertWeatherToDB(WeatherForecastResponse response){
        List<WeatherForecast> weatherData = response.getList();
        List<Weather> dataToSave = new ArrayList<>();
        for (WeatherForecast data : weatherData) {
            dataToSave.add(new Weather(
                    response.getCity().getName(), data.getDt(), data.getMain().getTemp()));
        }
        compositeDisposable.add(Observable.fromCallable(new Callable<long[]>() {
            @Override
            public long[] call() throws Exception {
                return AppDatabase.get().weatherDao().insertAll(dataToSave);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        //weathers = AppDatabase.get().weatherDao().getAll();
                    }
                }));
    }
}
