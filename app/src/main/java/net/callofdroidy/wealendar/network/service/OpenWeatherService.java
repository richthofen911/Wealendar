package net.callofdroidy.wealendar.network.service;

import net.callofdroidy.wealendar.network.jsonmodel.WeatherForecastResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {


    @GET("forecast")
    Observable<WeatherForecastResponse> get5DayForecast(@Query("id") long cityId, @Query("appid") String appId);

}
