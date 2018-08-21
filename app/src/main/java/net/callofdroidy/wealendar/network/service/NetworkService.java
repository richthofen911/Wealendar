package net.callofdroidy.wealendar.network.service;

import net.callofdroidy.core.network.HttpClient;
import net.callofdroidy.wealendar.network.jsonmodel.WeatherForecastResponse;
import net.callofdroidy.weathernow.BuildConfig;

import io.reactivex.Observable;

public class NetworkService {


    private static OpenWeatherService INSTANCE;

    public synchronized static OpenWeatherService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HttpClient().createHttpNetworkService(BuildConfig.BASE_URL, OpenWeatherService.class);
        }

        return INSTANCE;
    }

    public Observable<WeatherForecastResponse> get5DayForecast(long cityId, String appId) {
        return INSTANCE.get5DayForecast(cityId, appId);
    }
}
