package com.jt28.a6735.mirro.weather;

/**
 * Created by a6735 on 2017/7/14.
 */

public class HourForeCast {
    private String areaid;
    private String hour;
    private String weatherCondition;
    private Integer temp;

    public HourForeCast() {
    }

    public HourForeCast(String areaid, String hour, String weatherCondition, Integer temp) {
        this.areaid = areaid;
        this.hour = hour;
        this.weatherCondition = weatherCondition;
        this.temp = temp;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }
}
