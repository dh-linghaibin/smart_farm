package com.jt28.a6735.smart_farm;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.docom.docom_tts.TtsDemo;
import com.jt28.a6735.smart_farm.msg.FragmentCountMsg;
import com.jt28.a6735.weatherget.dao.greendao.Aqi;
import com.jt28.a6735.weatherget.dao.greendao.RealWeather;
import com.jt28.a6735.weatherget.manager.WeatherInfo;
import com.jt28.a6735.weatherget.manager.WeatherManager;
import com.jt28.a6735.weatherget.util.Constant;
import com.jt28.a6735.weatherget.widget.WeekForecastView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by a6735 on 2017/5/26.
 */

public class WeatherFragmnet extends Fragment implements View.OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weatherfragmnet, container, false);
    }

    private Button but_set;
    private TextView text_show;
    private String show_s = "";
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ttsDemo = new TtsDemo(getActivity());
        but_set = (Button) getActivity().findViewById(R.id.weather_but_set);
        but_set.setOnClickListener(this);
        text_show = (TextView) getActivity().findViewById(R.id.weather_text_show);

        weekForeCastView = (WeekForecastView) getActivity().findViewById(R.id.weekForecast);

        weatherManager = new WeatherManager(getActivity());
        refresh(false);
//        WeaterGet mWeaterGet = new WeaterGet();
//        mWeaterGet.getData();
//        mWeaterGet.setClickCallback(new WeaterGet.GetDatCallback() {
//            @Override
//            public void onBack(List<Weather> weather) {
//                show_s = weather.get(0).getCity() + weather.get(0).getTemp() + weather.get(0).getWind();
//                Tool.SendMsg(handler,0);
//            }
//        });
    }

    private void refresh(boolean useLocal) {
        weatherManager.refreshWeather(weatherID, useLocal,new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == Constant.MSG_ERROR) {
                    //Toast.makeText(MainActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
                    System.out.println("test" + "errow");
                } else {
                    WeatherInfo weatherInfo = (WeatherInfo) msg.obj;
                    Aqi aqi = weatherInfo.getAqi();
                    //实时
                    RealWeather realWeather = weatherInfo.getRealWeather();
                    System.out.println("test"+realWeather.getFeeltemp());

                    //周报&&时报
                    weekForeCastView.setForeCasts(weatherInfo.getWeekForeCasts());

                   // show_s = realWeather.getFeeltemp().toString();
                   // Tool.SendMsg(handler,0);
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //TODO now it's visible to user
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weather_but_set:
                //Tool.SendMsg(handler,0);
                EventBus.getDefault().post(new FragmentCountMsg("Hello everyone!"));
                break;
        }
    }

    private TtsDemo ttsDemo;

    //ui线程
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 0:
                text_show.setText(show_s);
                ttsDemo.startSpeak(show_s);
            break;
            case 1:
                break;
            default:
            break;
        }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        ttsDemo.stopSpeak();
    }

    private WeatherManager weatherManager;
    private String weatherID = "101210101";

    //周报 时报
    private WeekForecastView weekForeCastView;
}
