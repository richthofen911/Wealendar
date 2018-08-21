package net.callofdroidy.wealendar.repository;

import net.callofdroidy.wealendar.network.service.OpenWeatherService;

public class Weather {
    private OpenWeatherService openWeatherService;

    public Weather(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }
}
