package net.callofdroidy.wealendar.network.service;

import net.callofdroidy.core.network.HttpClient;
import net.callofdroidy.wealendar.BuildConfig;
import net.callofdroidy.wealendar.network.jsonmodel.WeatherForecastResponse;

import io.reactivex.Observable;

public class NetworkService {

    private static NetworkService INSTANCE;
    private OpenWeatherService openWeatherService;

    public synchronized static NetworkService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkService();
        }

        return INSTANCE;
    }

    private NetworkService() {
        openWeatherService = new HttpClient().createHttpNetworkService(BuildConfig.BASE_URL, OpenWeatherService.class);
    }

    public Observable<WeatherForecastResponse> get5DayForecast(long cityId, String appId) {
        return openWeatherService.get5DayForecast(cityId, appId);
    }
}
