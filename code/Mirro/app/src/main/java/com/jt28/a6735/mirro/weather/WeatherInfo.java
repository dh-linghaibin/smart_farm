package com.jt28.a6735.mirro.weather;

import java.util.List;

/**
 * Created by a6735 on 2017/7/14.
 */

public class WeatherInfo {
    private List<WeekForeCast> weekForeCasts;//一周天气情况
    private List<HourForeCast> hourForeCasts;//小时前期
    private RealWeather realWeather;//实际天气
    private Aqi aqi;
    private List<Zhishu> zhishu;
    private Alarms alarms;

    public List<WeekForeCast> getWeekForeCasts() {
        return weekForeCasts;
    }

    public void setWeekForeCasts(List<WeekForeCast> weekForeCasts) {
        this.weekForeCasts = weekForeCasts;
    }

    public List<HourForeCast> getHourForeCasts() {
        return hourForeCasts;
    }

    public void setHourForeCasts(List<HourForeCast> hourForeCasts) {
        this.hourForeCasts = hourForeCasts;
    }

    public RealWeather getRealWeather() {
        return realWeather;
    }

    public void setRealWeather(RealWeather realWeather) {
        this.realWeather = realWeather;
    }

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }


    public List<Zhishu> getZhishu() {
        return zhishu;
    }

    public void setZhishu(List<Zhishu> zhishu) {
        this.zhishu = zhishu;
    }

    public Alarms getAlarms() {
        return alarms;
    }

    public void setAlarms(Alarms alarms) {
        this.alarms = alarms;
    }
}
