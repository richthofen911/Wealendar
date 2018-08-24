package net.callofdroidy.wealendar.network.service;

import net.callofdroidy.wealendar.network.jsonmodel.MultipleWeatherCurrentResponse;
import net.callofdroidy.wealendar.network.jsonmodel.SingleWeatherCurrentResponse;
import net.callofdroidy.wealendar.network.jsonmodel.WeatherForecastResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {
    @GET("forecast")
    Observable<WeatherForecastResponse> get5DayForecast(
            @Query("id") String cityId, @Query("appid") String appId);

    @GET("group?units=metric")
    Observable<MultipleWeatherCurrentResponse> getMultipleCitiesCurrentWeather(
            @Query("group") String cityIds, @Query("appid") String appId);
}
