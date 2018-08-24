package net.callofdroidy.wealendar.network.service;

import net.callofdroidy.core.network.HttpClient;
import net.callofdroidy.wealendar.BuildConfig;
import net.callofdroidy.wealendar.R;
import net.callofdroidy.wealendar.application.Wealendar;
import net.callofdroidy.wealendar.network.jsonmodel.MultipleWeatherCurrentResponse;
import net.callofdroidy.wealendar.network.jsonmodel.SingleWeatherCurrentResponse;
import net.callofdroidy.wealendar.network.jsonmodel.WeatherForecastResponse;

import io.reactivex.Observable;

public class NetworkService {

    private static NetworkService INSTANCE;
    private OpenWeatherService openWeatherService;
    private String openWeatherApiKey;


    public synchronized static NetworkService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkService();
        }

        return INSTANCE;
    }

    private NetworkService() {
        openWeatherService = new HttpClient().createHttpNetworkService(BuildConfig.BASE_URL, OpenWeatherService.class);
        openWeatherApiKey = Wealendar.get().getString(R.string.open_weather_api_key);
    }

    public Observable<WeatherForecastResponse> get5DayForecast(String cityId) {
        return openWeatherService.get5DayForecast(cityId, openWeatherApiKey);
    }

    public Observable<MultipleWeatherCurrentResponse> getMultipleCitiesCurrentWeather(String cityIds) {
        return openWeatherService.getMultipleCitiesCurrentWeather(cityIds, openWeatherApiKey);
    }
}
