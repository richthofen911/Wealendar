package net.callofdroidy.wealendar.network.jsonmodel;

import java.util.List;

// Element in forecast response list
public class WeatherForecast {

    private long dt; // date
    private Main main;
    private List<Weather> weather;
    private String dt_txt; // data in readable text

    WeatherForecast() {

    }

    public long getDt() {
        return dt;
    }

    public Main getMain() {
        return main;
    }

    public List<Weather> getWeather() {
        return weather;
    }


    public String getDt_txt() {
        return dt_txt;
    }
}
