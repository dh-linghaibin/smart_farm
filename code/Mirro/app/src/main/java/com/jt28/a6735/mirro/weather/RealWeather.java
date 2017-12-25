package com.jt28.a6735.mirro.weather;

/**
 * Created by a6735 on 2017/7/14.
 */

public class RealWeather {

    private String areaid;
    private String areaName;
    private String weatherCondition;
    private String fx;
    private String fj;
    private Integer temp;
    private Integer feeltemp;
    private Integer shidu;
    private String sunrise;
    private String sundown;
    private java.util.Date lastUpdate;

    public RealWeather() {
    }

    public RealWeather(String areaid, String areaName, String weatherCondition, String fx, String fj, Integer temp, Integer feeltemp, Integer shidu, String sunrise, String sundown, java.util.Date lastUpdate) {
        this.areaid = areaid;
        this.areaName = areaName;
        this.weatherCondition = weatherCondition;
        this.fx = fx;
        this.fj = fj;
        this.temp = temp;
        this.feeltemp = feeltemp;
        this.shidu = shidu;
        this.sunrise = sunrise;
        this.sundown = sundown;
        this.lastUpdate = lastUpdate;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getFeeltemp() {
        return feeltemp;
    }

    public void setFeeltemp(Integer feeltemp) {
        this.feeltemp = feeltemp;
    }

    public Integer getShidu() {
        return shidu;
    }

    public void setShidu(Integer shidu) {
        this.shidu = shidu;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSundown() {
        return sundown;
    }

    public void setSundown(String sundown) {
        this.sundown = sundown;
    }

    public java.util.Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(java.util.Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
