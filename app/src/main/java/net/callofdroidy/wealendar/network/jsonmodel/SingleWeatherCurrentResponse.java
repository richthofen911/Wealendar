package net.callofdroidy.wealendar.network.jsonmodel;

import java.util.List;

public class SingleWeatherCurrentResponse {
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private long dt;
    private long id; // city ID
    private String name; // city name

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public long getDt() {
        return dt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
