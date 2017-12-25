package com.jt28.a6735.weatherget;

import com.jt28.a6735.weatherget.manager.WeatherManager;

/**
 * Created by a6735 on 2017/5/27.
 */

public class WeaterGet {
    private WeatherManager weatherManager;

    private GetDatCallback callback;
    //回调接口
    public interface GetDatCallback{
        void onBack();
    }

    public void setClickCallback(GetDatCallback callback) {
        this.callback = callback;
    }

    public void getData() {

    }
}
