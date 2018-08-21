package net.callofdroidy.wealendar.network.jsonmodel;

import java.util.List;

public class WeatherForecastResponse{
    private String cod;
    private float message;
    private int cnt;
    private List<WeatherData> list;
    private City city;

    WeatherForecastResponse() {

    }

    public String getCod() {
        return cod;
    }

    public float getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public List<WeatherData> getList() {
        return list;
    }

    public City getCity() {
        return city;
    }
}
