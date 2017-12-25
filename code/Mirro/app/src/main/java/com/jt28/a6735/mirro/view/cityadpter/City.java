package com.jt28.a6735.mirro.view.cityadpter;

/**
 * Created by a6735 on 2017/9/18.
 */

public class City {
    private String cityname;
    private String l_code;
    public City(String cityname,String l_code){
        this.cityname = cityname;
        this.l_code = l_code;
    }

    public String getCityname() {
        return cityname;
    }

    public String getL_code() {
        return l_code;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public void setL_code(String l_code) {
        this.l_code = l_code;
    }
}
