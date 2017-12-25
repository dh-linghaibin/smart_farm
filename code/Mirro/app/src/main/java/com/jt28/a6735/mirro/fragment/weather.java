package com.jt28.a6735.mirro.fragment;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jt28.a6735.mirro.Msg.Butcmd;
import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.tool.GetResources;
import com.jt28.a6735.mirro.view.BezierCurve;
import com.jt28.a6735.mirro.weather.Aqi;
import com.jt28.a6735.mirro.weather.RealWeather;
import com.jt28.a6735.mirro.weather.WeatherInfo;
import com.jt28.a6735.mirro.weather.WeekForeCast;
import com.jt28.a6735.viewpager.PageFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by a6735 on 2017/8/15.
 */

public class weather extends PageFragment implements View.OnClickListener{
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weather_dy:{
                EventBus.getDefault().post(new Butcmd("dy"));
            }
                break;
            case R.id.weather_ym:{
                EventBus.getDefault().post(new Butcmd("ym"));
            }
                break;
            case R.id.weather_lw:{
                EventBus.getDefault().post(new Butcmd("lw"));
            }
                break;
            case R.id.weather_mz:{
                EventBus.getDefault().post(new Butcmd("mz"));
            }
                break;
        }
    }

    //ui线程
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    WeatherInfo weather = (WeatherInfo)msg.obj;
                    RealWeather realWeather = weather.getRealWeather();
                    Aqi aqi = weather.getAqi();
                    Resources resources = getActivity().getBaseContext().getResources();

                    temp.setText(String.valueOf(realWeather.getTemp()) + "°");
                    ud_temp.setText(String.valueOf(weather.getWeekForeCasts().get(0).getTempL()) + "°/" + String.valueOf(weather.getWeekForeCasts().get(0).getTempH()) + "°");
                    wind.setText(realWeather.getFx()+": "+realWeather.getFj()+"级");
                    wer.setText(realWeather.getWeatherCondition());
                    room_air.setText(String.valueOf(aqi.getQuality()));

                    Drawable imageDrawable = resources.getDrawable(getIconResId(realWeather.getWeatherCondition()));
                    wer_img.setBackgroundDrawable(imageDrawable);
                    if(realWeather.getWeatherCondition().equals("晴")) {
                        wer_tool.setVisibility(View.VISIBLE);
                        wer_tool.setText("防晒");
                    } else if(realWeather.getWeatherCondition().equals("小雨")
                            | realWeather.getWeatherCondition().equals("雨")
                            | realWeather.getWeatherCondition().equals("中雨")
                            | realWeather.getWeatherCondition().equals("阵雨")
                            | realWeather.getWeatherCondition().equals("雷阵雨")
                            | realWeather.getWeatherCondition().equals("雨夹雪")) {
                        wer_tool.setVisibility(View.VISIBLE);
                        wer_tool.setText("带伞");
                    } else {
                        wer_tool.setVisibility(View.GONE);
                    }

                    for(int z_i = 0;z_i < 7;z_i++) {
                        if (weather.getHourForeCasts().size() > z_i) {
                            Drawable imgs = resources.getDrawable(getIconResIds(weather.getHourForeCasts().get(z_i).getWeatherCondition()));
                            zs_img[z_i].setBackgroundDrawable(imgs);
                            zs_time[z_i].setText(String.valueOf(weather.getHourForeCasts().get(z_i).getHour()));
                        }
                    }

                    String[] week = new String[7];
                    String[] week_weather = new String[7];
                    int[] week_temp_h = new int[7];
                    int[] week_temp_l = new int[7];
                    int week_num = 0;
                    for(WeekForeCast tmp:weather.getWeekForeCasts()) {
                        Date d = tmp.getWeatherDate();
                        week[week_num] = GetResources.getWeek(d);
                        week_weather[week_num] = tmp.getWeatherConditionEnd();
                        week_temp_h[week_num] = tmp.getTempH();
                        week_temp_l[week_num] = tmp.getTempL();
                        week_num++;
                    }
                    Log.d("lhb", Arrays.toString(week_temp_h));
                    mBezierCurve.SetTAl(week);
                    mBezierCurve.SetTAI_TOU_tq(week_weather);
                    mBezierCurve.SetTemp(week_temp_h,week_temp_l);
                    mBezierCurve.start();
                    break;
                case 1:
                    EventBus.getDefault().post(new Butcmd("weatherget"));
                    break;
                default:
                    break;
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WeatherInfo event) {
        Message message=new Message();
        message.what=0;
        message.obj = event;
        handler.sendMessage(message);
    }

    //为了每次显示动画
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Butcmd event) {
        switch (event.getCmd()) {
            case "weatheropen": {
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
                break;
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBezierCurve = getActivity().findViewById(R.id.weather_beziercurve);
        mBezierCurve.setLoop(false);
        //mBezierCurve.start();

        temp  = getActivity().findViewById(R.id.weather_temp);
        ud_temp  = getActivity().findViewById(R.id.weather_updowntemp);
        wer  = getActivity().findViewById(R.id.weather_wer);
        wer_img  = getActivity().findViewById(R.id.weather_wer_img);
        wer_tool  = getActivity().findViewById(R.id.weather_wind_tool);
        wind  = getActivity().findViewById(R.id.weather_wind);
        room_air  = getActivity().findViewById(R.id.weather_roomair);
        zs_img[0]  = getActivity().findViewById(R.id.weather_zs_img1);
        zs_time[0]  = getActivity().findViewById(R.id.weather_zs_time1);
        zs_img[1]  = getActivity().findViewById(R.id.weather_zs_img2);
        zs_time[1]  = getActivity().findViewById(R.id.weather_zs_time2);
        zs_img[2]  = getActivity().findViewById(R.id.weather_zs_img3);
        zs_time[2]  = getActivity().findViewById(R.id.weather_zs_time3);
        zs_img[3]  = getActivity().findViewById(R.id.weather_zs_img4);
        zs_time[3]  = getActivity().findViewById(R.id.weather_zs_time4);
        zs_img[4]  = getActivity().findViewById(R.id.weather_zs_img5);
        zs_time[4]  = getActivity().findViewById(R.id.weather_zs_time5);
        zs_img[5]  = getActivity().findViewById(R.id.weather_zs_img6);
        zs_time[5]  = getActivity().findViewById(R.id.weather_zs_time6);
        zs_img[6]  = getActivity().findViewById(R.id.weather_zs_img7);
        zs_time[6]  = getActivity().findViewById(R.id.weather_zs_time7);
        but_dy  = getActivity().findViewById(R.id.weather_dy);
        but_ym  = getActivity().findViewById(R.id.weather_ym);
        but_lw  = getActivity().findViewById(R.id.weather_lw);
        but_mz  = getActivity().findViewById(R.id.weather_mz);
        but_dy.setOnClickListener(this);
        but_ym.setOnClickListener(this);
        but_lw.setOnClickListener(this);
        but_mz.setOnClickListener(this);

        newwindsys = getActivity().findViewById(R.id.weather_newwindsys);
        newwindsys_img = getActivity().findViewById(R.id.weather_newwindsys_img);
        newwindsys_show = getActivity().findViewById(R.id.weather_newwindsys_show);
        final Resources resources = getActivity().getResources();
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run () {
                Drawable imageDrawable;
                if(show_tabl) {
                    if (sho_flag == 1) {
                        sho_flag = 0;
//                        stopRotate();
//                        imageDrawable = resources.getDrawable(R.mipmap.icon_fan_no);
//                        newwindsys_show.setText("关");
                    } else {
                        sho_flag = 1;
                        startRotate();
                        imageDrawable = resources.getDrawable(R.mipmap.icon_fan_off);
                        newwindsys_show.setText("开");
                        newwindsys_img.setBackgroundDrawable(imageDrawable);
                    }
//                    newwindsys_img.setBackgroundDrawable(imageDrawable);
                }
                //Log.d(TAG,"更新");
                handler.postDelayed(this,20000);
            }
        };
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,2000);
    }

    /**
     * 开启动画
     */
    public void startRotate(){
        Animation operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.version_image_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if(operatingAnim!=null){
            newwindsys_img.startAnimation(operatingAnim);
        }
    }
    /**
     * 关闭动画
     */
    public void stopRotate(){
        newwindsys_img.clearAnimation();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//
//        super.onConfigurationChanged(newConfig);
//
//        if (operatingAnim != null && infoOperatingIV != null && operatingAnim.hasStarted()) {
//            infoOperatingIV.clearAnimation();
//            infoOperatingIV.startAnimation(operatingAnim);
//        }
//    }

    private boolean show_tabl = false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //TODO now it's visible to user
            show_tabl = true;
        } else {
            //TODO now it's invisible to user
            show_tabl = false;
        }
        Log.d("lhb","天气显示--------");
//        Message message=new Message();
//        message.what=1;
//        handler.sendMessage(message);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        Log.d("lhb","天气开启");
        Message message=new Message();
        message.what=1;
        handler.sendMessage(message);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        Log.d("lhb","天气关闭");
    }


    private BezierCurve mBezierCurve;//曲线图


    private RelativeLayout but_dy,but_ym,but_lw,but_mz;
    private TextView temp;//温度
    private TextView ud_temp;//最高温度
    private TextView wer;//天气
    private ImageView wer_img;//天气图片
    private TextView wer_tool;//提示是否需要带伞
    private TextView wind;//风力
    private TextView room_air;//室内空气

    private ImageView[] zs_img = new ImageView[7];//逐时预报
    private TextView[] zs_time = new TextView[7];

    private LinearLayout newwindsys;//新风系统
    private ImageView newwindsys_img;
    private TextView newwindsys_show;
    private int sho_flag = 0;

    private int getIconResId(String weather) {
        int resId;
        switch (weather) {
            case "晴":
                resId = R.mipmap.icon_sunny_96;
                break;
            case "阴":
                resId = R.mipmap.icon_cloudy_96;
                break;
            case "多云":
                resId = R.mipmap.icon_overcast_96;
                break;
            case "雾":
                resId = R.mipmap.icon_fog_96;
                break;
            case "雨":
                resId = R.mipmap.icon_rain_96;
                break;
            case "小雨":
                resId = R.mipmap.icon_rain_96;
                break;
            case "小雪":
                resId = R.mipmap.icon_heavysnow_96;
                break;
            case "中雨":
                resId = R.mipmap.icon_rain_96;
                break;
            case "阵雨":
                resId = R.mipmap.icon_shower_96;
                break;
            case "雷阵雨":
                resId = R.mipmap.icon_thunderstorm_96;
                break;
            case "阵雪":
                resId = R.mipmap.icon_snowshower_96;
                break;
            case "中雪":
                resId = R.mipmap.icon_sleet_96;
                break;
            case "大雪":
                resId = R.mipmap.icon_snow_96;
                break;
            case "雨夹雪":
                resId = R.mipmap.icon_sleet_96;
                break;
            case "霾":
                resId = R.mipmap.icon_fog_96;
                break;
            case "雷":
                resId = R.mipmap.icon_thunderstorm_96;
                break;
            default:
                resId = R.mipmap.icon_sunny_96;
                break;
        }
        return resId;
    }

    private int getIconResIds(String weather) {
        int resId;
        switch (weather) {
            case "晴":
                resId = R.mipmap.icon_sunny_48;
                break;
            case "阴":
                resId = R.mipmap.icon_cloudy_48;
                break;
            case "多云":
                resId = R.mipmap.icon_overcast_48;
                break;
            case "雾":
                resId = R.mipmap.icon_fog_48;
                break;
            case "雨":
                resId = R.mipmap.icon_rain_48;
                break;
            case "小雨":
                resId = R.mipmap.icon_rain_48;
                break;
            case "小雪":
                resId = R.mipmap.icon_heavysnow_48;
                break;
            case "中雨":
                resId = R.mipmap.icon_rain_48;
                break;
            case "阵雨":
                resId = R.mipmap.icon_shower_48;
                break;
            case "雷阵雨":
                resId = R.mipmap.icon_thunderstorm_48;
                break;
            case "阵雪":
                resId = R.mipmap.icon_snowshower_48;
                break;
            case "中雪":
                resId = R.mipmap.icon_sleet_48;
                break;
            case "大雪":
                resId = R.mipmap.icon_snow_48;
                break;
            case "雨夹雪":
                resId = R.mipmap.icon_sleet_48;
                break;
            case "霾":
                resId = R.mipmap.icon_fog_48;
                break;
            case "雷":
                resId = R.mipmap.icon_thunderstorm_48;
                break;
            default:
                resId = R.mipmap.icon_sunny_48;
                break;
        }
        return resId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather, container, false);
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser){
//            //TODO now it's visible to user
//        }
//    }
}
