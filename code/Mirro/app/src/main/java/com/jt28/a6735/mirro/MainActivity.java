package com.jt28.a6735.mirro;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.jt28.a6735.mirro.Msg.Butcmd;
import com.jt28.a6735.mirro.Msg.TackingMsg;
import com.jt28.a6735.mirro.movelayout.DragerViewLayout;
import com.jt28.a6735.mirro.movelayout.util.SharedPreferencesUtil;
import com.jt28.a6735.mirro.tool.App.app_save;
import com.jt28.a6735.mirro.tool.GpsUtil;
import com.jt28.a6735.mirro.tool.TextUtil;
import com.jt28.a6735.mirro.view.NewsAdpter.ResultBean;
import com.jt28.a6735.mirro.view.messageadpter.message;
import com.jt28.a6735.mirro.weather.City;
import com.jt28.a6735.mirro.weather.CityDao;
import com.jt28.a6735.share.Share;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements BDLocationListener {
    private DragerViewLayout drager_layout;
    private LinearLayout choosecitylayout;//城市设置
    private LinearLayout main_menu;
    private String TAG = "lhb";

    //语音识别用的
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //权限申请
        Accessibility();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            //initParam();
        }
        GpsUtil gpsUtil = new GpsUtil(this, this);
        gpsUtil.start();
        //语音识别
        SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID +"=56f22e12");
        mGson = new Gson();
//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        final PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "TAG");
//        wakeLock.acquire();
//        //然后定时
//        new Handler().postDelayed(new Runnable(){
//            public void run(){
//                //wakeLock.release();//
//                Log.d(TAG,"灭屏!!!");
//                Intent intent =  new Intent(Settings.ACTION_SETTINGS);
//                startActivity(intent);
//            }
//        }, 2*1000);//延时10秒灭屏
        getFile(Environment.getExternalStorageDirectory().toString() + File.separator + "sample");

        drager_layout = (DragerViewLayout) findViewById(R.id.drager_layout);
        drager_layout.isDrager(false);
        drager_layout.setFilePathAndName(Environment.getExternalStorageDirectory().getPath() + "/", "lhb");

        choosecitylayout = (LinearLayout) findViewById(R.id.main_choosecity);
        main_menu = (LinearLayout) findViewById(R.id.main_mainmenu);
    }

    public void onRecognise(View view) {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, null);
        //2.设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
        //结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener(mRecognizerDialogListener);
        //4.显示dialog，接收语音输入
        mDialog.show();
    }

    private RecognizerDialogListener mRecognizerDialogListener =  new RecognizerDialogListener() {
        /**
         *
         * @param recognizerResult 语音识别结果
         * @param b true表示是标点符号
         */
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            // Toast.makeText(MainActivity.this, recognizerResult.getResultString(), Toast.LENGTH_LONG).show();
            if (b) {
                return;
            }
            ResultBean resultBean = mGson.fromJson(recognizerResult.getResultString(), ResultBean.class);
            List<ResultBean.WsBean> ws = resultBean.getWs();
            String w = "";
            for (int i = 0; i < ws.size(); i++) {
                List<ResultBean.WsBean.CwBean> cw = ws.get(i).getCw();
                for (int j = 0; j < cw.size(); j++) {
                    w += cw.get(j).getW();
                }
            }
            Toast.makeText(MainActivity.this, w, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final TackingMsg event) {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).run();
    }


    private int page_flag= 0;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final Butcmd event) {
        /* Do something */
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (event.getCmd()) {
                    case "setmovechange":{
//                        if(drager_layout.isDrager()) {
//                            drager_layout.isDrager(false);
//                        } else {
//                            choosecitylayout.setVisibility(View.GONE);
//                            drager_layout.isDrager(true);
//                        }
                        main_menu.setVisibility(View.GONE);
                        choosecitylayout.setVisibility(View.GONE);

                        String xy = (String) SharedPreferencesUtil.getData(getApplication(), "one", "0");
                        if (!xy.equals("0")){
                            String[] xys = xy.split("#");
                            if (xys.length == 2) {
                                if(Integer.parseInt(xys[0]) < 820) {
                                    SharedPreferencesUtil.saveData(getApplication(), "one", 1300 + "#" + 50);
                                    SharedPreferencesUtil.saveData(getApplication(), "tow", 150 + "#" + 50);
                                } else {
                                    SharedPreferencesUtil.saveData(getApplication(), "one", 120 + "#" + 50);
                                    SharedPreferencesUtil.saveData(getApplication(), "tow", 720 + "#" + 50);
                                }
                            } else {
                                SharedPreferencesUtil.saveData(getApplication(), "one", 1300 + "#" + 50);
                                SharedPreferencesUtil.saveData(getApplication(), "tow", 150 + "#" + 50);
                            }
                            Log.d("lhb", Arrays.toString(xys));
                        } else {
                            SharedPreferencesUtil.saveData(getApplication(), "one", 1300 + "#" + 50);
                            SharedPreferencesUtil.saveData(getApplication(), "tow", 150 + "#" + 50);
                        }

                        main_menu.setVisibility(View.VISIBLE);
                    }
                    break;
                    case "choosecity":{
                        if(page_flag == 1) {
                            page_flag = 2;
                            choosecitylayout.setVisibility(View.GONE);
                        } else {
                            page_flag = 1;
                            choosecitylayout.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                    case "weatheropen":{
                        if(page_flag == 3) {
                            page_flag = 4;
                            choosecitylayout.setVisibility(View.GONE);
                        } else {
                            page_flag = 3;
                            choosecitylayout.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                    case "messageboardopen":{
                        if(page_flag == 5) {
                            page_flag = 6;
                            choosecitylayout.setVisibility(View.GONE);
                        } else {
                            page_flag = 5;
                            choosecitylayout.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                    case "newsclose":{
                        if(page_flag == 7) {
                            page_flag = 8;
                            choosecitylayout.setVisibility(View.GONE);
                        } else {
                            page_flag = 7;
                            choosecitylayout.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                    case "close":{
                        choosecitylayout.setVisibility(View.GONE);
                        page_flag = 0;
                    }
                    break;
                    case "updatasave":{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(m_message.size() > 80) {
                                    m_message.remove(0);
                                }
                                Share.putObject("m_message",m_message);
                                Share.putObject("s_appInfos",s_appInfos);
                            }
                        }).run();
                    }
                    break;
                }
            }
        }).run();
    };

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Log.d("aaa", bdLocation.getCity() + bdLocation.getDistrict());
        String areaName = TextUtil.getFormatArea(bdLocation.getDistrict());
        String cityName = TextUtil.getFormatArea(bdLocation.getCity());
        City city = cityDao.getCityByCityAndArea(cityName, areaName);
    }

    public static List<message> m_message = new ArrayList<>();
    public static List<app_save> s_appInfos = new ArrayList<>();
    public static String weatherID = "101210108";

    private void getFile(String url) {
        Log.d("lhb",url);
        File file = new File(url);
        if (!file.exists()) {
            file.mkdirs();
        }
        Share.init("CACHE", 10 * 1024, file.toString());

        weatherID = Share.getString("weatherID");
        if(weatherID == null) {
            weatherID = "101210108";
        }
        m_message = (List<message>)Share.getObject("m_message");
        if(m_message == null) {
            m_message = new ArrayList<>();
        }

        s_appInfos = (List<app_save>)Share.getObject("s_appInfos");
        if(s_appInfos == null) {
            s_appInfos = new ArrayList<>();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Override
    protected void onPause() {
super.onPause();
    }

    private final int REQUEST_CODE_ASK_CALL_PHONE = 123;
    /**
     * 对于6.0以后的机器动态权限申请
     */
    /**
     * 对于6.0以后的机器动态权限申请
     */
    public void Accessibility() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO);
            if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED && checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                //initViews();
            }
        } else {
            //initViews();
        }
    }

    protected void getPermission(final String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
        } else {
            //initParam();
        }
    }

    private CityDao cityDao = new CityDao(this);

}
