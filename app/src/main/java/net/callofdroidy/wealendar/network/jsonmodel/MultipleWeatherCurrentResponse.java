package net.callofdroidy.wealendar.network.jsonmodel;

import java.util.List;

public class MultipleWeatherCurrentResponse {
    private int cnt;
    private List<SingleWeatherCurrentResponse> list;

    public int getCnt() {
        return cnt;
    }

    public List<SingleWeatherCurrentResponse> getList() {
        return list;
    }
}
