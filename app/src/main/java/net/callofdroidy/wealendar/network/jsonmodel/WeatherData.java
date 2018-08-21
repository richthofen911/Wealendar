package net.callofdroidy.wealendar.network.jsonmodel;

import java.util.List;

public class WeatherData {

    private long dt; // date
    private Main main;
    private List<WeatherDescription> weather;
    private Clouds clouds;
    private Wind wind;
    private Snow snow;
    private Sys sys;
    private String dt_txt; // data in readable text

    WeatherData() {

    }

    public long getDt() {
        return dt;
    }

    public Main getMain() {
        return main;
    }

    public List<WeatherDescription> getWeather() {
        return weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public Snow getSnow() {
        return snow;
    }

    public Sys getSys() {
        return sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
