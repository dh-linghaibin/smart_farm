package com.jt28.a6735.mirro.weather;

/**
 * Created by a6735 on 2017/7/14.
 */

public class Aqi {
    private String areaid;
    private Integer aqi;
    private String quality;
    private Integer pm2_5;
    private Integer pm10;
    private Integer so2;
    private Integer no2;

    public Aqi() {
    }

    public Aqi(String areaid, Integer aqi, String quality, Integer pm2_5, Integer pm10, Integer so2, Integer no2) {
        this.areaid = areaid;
        this.aqi = aqi;
        this.quality = quality;
        this.pm2_5 = pm2_5;
        this.pm10 = pm10;
        this.so2 = so2;
        this.no2 = no2;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Integer getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(Integer pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public Integer getPm10() {
        return pm10;
    }

    public void setPm10(Integer pm10) {
        this.pm10 = pm10;
    }

    public Integer getSo2() {
        return so2;
    }

    public void setSo2(Integer so2) {
        this.so2 = so2;
    }

    public Integer getNo2() {
        return no2;
    }

    public void setNo2(Integer no2) {
        this.no2 = no2;
    }
}
