package com.jt28.a6735.mirro.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jt28.a6735.mirro.Msg.Butcmd;
import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.movelayout.util.SharedPreferencesUtil;
import com.jt28.a6735.mirro.tool.App.ApkTool;
import com.jt28.a6735.mirro.tool.App.AppUtil;
import com.jt28.a6735.mirro.tool.App.MyAppInfo;
import com.jt28.a6735.mirro.tool.App.app_save;
import com.jt28.a6735.mirro.tool.DensityUtil;
import com.jt28.a6735.mirro.tool.news.BigNews;
import com.jt28.a6735.mirro.tool.news.NewsSpiderThread;
import com.jt28.a6735.mirro.tool.news.NewsSummary;
import com.jt28.a6735.mirro.tool.news.NewsSummaryLab;
import com.jt28.a6735.mirro.tool.news.NewsSummaryListSpiderThread;
import com.jt28.a6735.mirro.view.Appadpter.AppAdapter;
import com.jt28.a6735.mirro.view.Appadpter.MyAppInfoch;
import com.jt28.a6735.mirro.view.NewsAdpter.NewAdpter;
import com.jt28.a6735.mirro.view.NewsAdpter.bignewsbean;
import com.jt28.a6735.mirro.view.NewsAdpter.newsbean;
import com.jt28.a6735.mirro.view.cityadpter.City;
import com.jt28.a6735.mirro.view.messageadpter.message;
import com.jt28.a6735.mirro.view.messageadpter.messageAadapter;
import com.jt28.a6735.mirro.weather.Aqi;
import com.jt28.a6735.mirro.weather.Constant;
import com.jt28.a6735.mirro.weather.RealWeather;
import com.jt28.a6735.mirro.weather.WeatherInfo;
import com.jt28.a6735.mirro.weather.WeatherManager;
import com.jt28.a6735.share.Share;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.jt28.a6735.mirro.MainActivity.m_message;
import static com.jt28.a6735.mirro.MainActivity.s_appInfos;
import static com.jt28.a6735.mirro.MainActivity.weatherID;

/**
 * Created by a6735 on 2017/8/14.
 */

public class mainmenu extends Fragment {
    private String TAG = "lhb";
    private boolean chose_dr = false;
    private static ImageView sys_view;

    void but_set() {
        sys_view = getActivity().findViewById(R.id.mainmenu_setleftopen);
        if(chose_dr) {
            weatheropen = getActivity().findViewById(R.id.mainmenu_werleftopen);
        } else {
            weatheropen = getActivity().findViewById(R.id.mainmenu_werleftopen2);
            sys_view.setVisibility(View.VISIBLE);
        }
        weatheropen.setVisibility(View.VISIBLE);
        resources = getActivity().getBaseContext().getResources();
        //Drawable imageDrawable = resources.getDrawable(R.drawable.btn_weatheropen); //图片在drawable目录下
        // weatheropen.setBackgroundDrawable(imageDrawable);
        weatheropen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new Butcmd("weatheropen"));
                //Drawable imageDrawable = resources.getDrawable(R.drawable.btn_weatherclose); //图片在drawable目录下
                //weatheropen.setBackgroundDrawable(imageDrawable);
            }
        });
        if(chose_dr) {
            messageboardopen = getActivity().findViewById(R.id.mainmenu_msgleftopen);
        } else {
            messageboardopen = getActivity().findViewById(R.id.mainmenu_msgleftopen2);
        }
        messageboardopen.setVisibility(View.VISIBLE);

        messageboardopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new Butcmd("messageboardopen"));
            }
        });

        //---界面按钮
        if(chose_dr) {
            newleftopen = getActivity().findViewById(R.id.mainmenu_newleftopen);
        } else {
            newleftopen = getActivity().findViewById(R.id.mainmenu_newleftopen2);
        }
        newleftopen.setVisibility(View.VISIBLE);

        newleftopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new Butcmd("newsclose"));
            }
        });

        //--加载app
        if(chose_dr) {
            app_add = getActivity().findViewById(R.id.mainmenu_appleftopen);
        } else {
            app_add = getActivity().findViewById(R.id.mainmenu_appleftopen2);
        }
        app_add.setVisibility(View.VISIBLE);

        app_add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                AddApp();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String xy = (String) SharedPreferencesUtil.getData(getActivity(), "one", "0");
        Log.d("lhb",xy);
        if (!xy.equals("0")) {
            String[] xys = xy.split("#");
            if (xys.length == 2) {
                if(Integer.parseInt(xys[0]) < 500) {
                    chose_dr = true;
                } else {
                    chose_dr = false;
                }
            }
            Log.d("lhb", Arrays.toString(xys));
        } else {
            chose_dr = true;
        }

        //天气
        show_temp = getActivity().findViewById(R.id.mainmenu_temp);
        show_addr = getActivity().findViewById(R.id.mainmenu_addr);
        show_itel = getActivity().findViewById(R.id.mainmenu_itel);
        show_time = getActivity().findViewById(R.id.mainmenu_timer);
        show_year = getActivity().findViewById(R.id.mainmenu_year);
        show_week = getActivity().findViewById(R.id.mainmenu_week);
        show_temp_up = getActivity().findViewById(R.id.mainmenu_ud_temp);
        weat_img = getActivity().findViewById(R.id.mainmenu_weaimg);
        openchoosecity = getActivity().findViewById(R.id.mainmenu_openchoosecity);
        openchoosecity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new Butcmd("choosecity"));
            }
        });


        final SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
        final SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd ");
        final SimpleDateFormat formatter3 = new SimpleDateFormat("EEEE", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter1.format(curDate);
        show_time.setText(str);
        str = formatter2.format(curDate);
        show_year.setText(str);
        str = formatter3.format(curDate);
        show_week.setText(str);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run () {
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter1.format(curDate);
                show_time.setText(str);
                str = formatter2.format(curDate);
                show_year.setText(str);
                str = formatter3.format(curDate);
                show_week.setText(str);
                //Log.d(TAG,"更新");
                handler.postDelayed(this,3000);
            }
        };
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,1000);


        weatherManager = new WeatherManager(getActivity());
//        Message message=new Message();
//        message.obj = new City("杭州","101210101");
//        message.what=1;
//        handler.sendMessage(message);

        Runnable runnable_weather = new Runnable() {
            public void run () {
                weatherManager.refreshWeather(weatherID, false,new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == Constant.MSG_ERROR) {
                            Log.d(TAG, "errow");
                        } else {
                            weatherInfo = (WeatherInfo) msg.obj;
                            Aqi aqi = weatherInfo.getAqi();
                            //实时
                            RealWeather realWeather = weatherInfo.getRealWeather();

                            show_temp.setText( String.valueOf(realWeather.getTemp()) + "°" );
                            show_addr.setText( realWeather.getAreaName() );
                            show_temp_up.setText(String.valueOf(weatherInfo.getWeekForeCasts().get(0).getTempL()) + "°/" + String.valueOf(weatherInfo.getWeekForeCasts().get(0).getTempH()) + "°");
                            Drawable imageDrawable = resources.getDrawable(getIconResId(realWeather.getWeatherCondition()));
                            weat_img.setBackgroundDrawable(imageDrawable);

                            if(realWeather.getWeatherCondition().equals("晴")) {
                                show_itel.setVisibility(View.VISIBLE);
                                show_itel.setText("防晒");
                            } else if(realWeather.getWeatherCondition().equals("小雨")
                                    | realWeather.getWeatherCondition().equals("雨")
                                    | realWeather.getWeatherCondition().equals("中雨")
                                    | realWeather.getWeatherCondition().equals("阵雨")
                                    | realWeather.getWeatherCondition().equals("雷阵雨")
                                    | realWeather.getWeatherCondition().equals("雨夹雪")) {
                                show_itel.setVisibility(View.VISIBLE);
                                show_itel.setText("带伞");
                            } else {
                                show_itel.setVisibility(View.GONE);
                            }
                            EventBus.getDefault().post(weatherInfo);
                        }
                    }
                });
                handler.postDelayed(this,1800000);
            }
        };
        handler.removeCallbacks(runnable_weather);
        handler.postDelayed(runnable_weather,10);

        //留言
        l_message = getActivity().findViewById(R.id.mainmenu_message);
        l_message.setItemAnimator(new DefaultItemAnimator());
        l_message.setLayoutManager(new GridLayoutManager(getActivity(),1));
        m_messageAadapter = new messageAadapter(getActivity(),m_message);
        l_message.setAdapter(m_messageAadapter);

        //新闻
        l_news = getActivity().findViewById(R.id.mainmenu_news);
        l_news.setItemAnimator(new DefaultItemAnimator());
        l_news.setLayoutManager(new GridLayoutManager(getActivity(),1));
        m_NewAdpter = new NewAdpter(getActivity(), m_news, new NewAdpter.Completecallback() {
            @Override
            public void Autoreadcomplete(int pos) {
                int p ;
                if(pos > m_news.size()-2) {
                    p = pos;
                } else {
                    p = pos+2;
                }
                l_news.smoothScrollToPosition(p);
                Log.d(TAG,"smoothScrollToPosition 回调" + pos + p);
            }
        });
        l_news.setAdapter(m_NewAdpter);
        new Thread(new NewsSummaryListSpiderThread(mHandlernew)).start();
        //--语音播报
        Button voive = getActivity().findViewById(R.id.mainmenu_news_voice);
        voive.setOnClickListener(new View.OnClickListener() {
            boolean start = true;
            @Override
            public void onClick(View view) {
                int m_news_num = 0;
                //int m_news_num_bit = 0;
                for(newsbean tem: m_news){
                    if(tem.getButtonstatus() > 0) {
                        start = false;
                        if(tem.getButtonstatus() == 1) {
                            tem.setButtonstatus(2);
                        } else {
                            tem.setButtonstatus(1);
                        }
                        break;
                    }
                    //m_news_num++;
                }
                if(start) {
                    m_news.get(0).setButtonstatus(1);
                }
                //l_news.smoothScrollToPosition(m_news_num);
                m_NewAdpter.notifyDataSetChanged();

            }
        });


        //app
        l_app_list = getActivity().findViewById(R.id.mainmenu_applist);
        l_app_list.setItemAnimator(new DefaultItemAnimator());
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        l_app_list.setLayoutManager(linearLayoutManager);

        List<MyAppInfo> get_a = ApkTool.scanLocalInstallAppList(getActivity().getPackageManager(),getActivity());
        get_app.clear();
        for(MyAppInfo tmp:get_a) {
            for(app_save tmp_s:s_appInfos) {
                if(tmp.getPackname().equals(tmp_s.getPackname())) {
                    get_app.add(tmp);
                }
            }
        }

        app_appadapter = new AppAdapter(getActivity(),get_app);
        l_app_list.setAdapter(app_appadapter);
        app_appadapter.setAction(new AppAdapter.Chickaction() {
            @Override
            public void onData(String url) {
                AppUtil.startAPP(getActivity(),url);
            }
        });


        //设置
        but_setting = getActivity().findViewById(R.id.mainmenu_setting);
        //打开设置界面
        but_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EventBus.getDefault().post(new Butcmd("setmovechange"));
                Intent intent =  new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });
        but_photo = getActivity().findViewById(R.id.mainmenu_photo);
        but_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* 开启Pictures画面Type设定为image */
                Intent intent = new Intent();
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }
        });
        but_system = getActivity().findViewById(R.id.mainmenu_system);
        but_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
////                pm.acquire(SystemClock.uptimeMillis());
//                PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "TAG");  wakeLock.acquire();
//                wakeLock.release();
                EventBus.getDefault().post(new Butcmd("setmovechange"));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        weatheropen.setVisibility(View.GONE);
                        messageboardopen.setVisibility(View.GONE);
                        newleftopen.setVisibility(View.GONE);
                        app_add.setVisibility(View.GONE);
                        sys_view.setVisibility(View.GONE);
                        chose_dr = !chose_dr;
                        but_set();
                    }
                }).run();
            }
        });
        but_set();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //TODO now it's visible to user
        }
    }

    //ui线程
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    m_messageAadapter = new messageAadapter(getActivity(),m_message);
                    l_message.setAdapter(m_messageAadapter);
                    break;
                case 1:
                    EventBus.getDefault().post(new Butcmd("choosecity"));
                    City mcity = (City)msg.obj;
                    weatherID = mcity.getL_code();

                    Share.putString("weatherID",weatherID);

                    Log.d("lhb","开始读取天气" + weatherID);
                    weatherManager.refreshWeather(weatherID, false,new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            if (msg.what == Constant.MSG_ERROR) {
                                Log.d(TAG, "errow");
                            } else {
                                weatherInfo = (WeatherInfo) msg.obj;
                                Aqi aqi = weatherInfo.getAqi();
                                //实时
                                RealWeather realWeather = weatherInfo.getRealWeather();

                                show_temp.setText( String.valueOf(realWeather.getTemp()) + "°" );
                                show_addr.setText( realWeather.getAreaName() );
                                show_temp_up.setText(String.valueOf(weatherInfo.getWeekForeCasts().get(0).getTempL()) + "°/" + String.valueOf(weatherInfo.getWeekForeCasts().get(0).getTempH()) + "°");
                                Drawable imageDrawable = resources.getDrawable(getIconResId(realWeather.getWeatherCondition()));
                                weat_img.setBackgroundDrawable(imageDrawable);

                                if(realWeather.getWeatherCondition().equals("晴")) {
                                    show_itel.setVisibility(View.VISIBLE);
                                    show_itel.setText("防晒");
                                } else if(realWeather.getWeatherCondition().equals("小雨")
                                        | realWeather.getWeatherCondition().equals("雨")
                                        | realWeather.getWeatherCondition().equals("中雨")
                                        | realWeather.getWeatherCondition().equals("阵雨")
                                        | realWeather.getWeatherCondition().equals("雷阵雨")
                                        | realWeather.getWeatherCondition().equals("雨夹雪")) {
                                    show_itel.setVisibility(View.VISIBLE);
                                    show_itel.setText("带伞");
                                } else {
                                    show_itel.setVisibility(View.GONE);
                                }
                                EventBus.getDefault().post(weatherInfo);
                            }
                        }
                    });
                    break;
                case 2:{
                    EventBus.getDefault().post(m_news_m);
                }
                break;
                default:
                    break;
            }
        }
    };

    //天气
    private Resources resources;
    private WeatherManager weatherManager;

    private TextView show_temp;
    private TextView show_temp_up;
    private TextView show_addr;
    private ImageView weat_img;
    private TextView show_itel;
    private TextView show_time;
    private TextView show_year;
    private TextView show_week;

    private static ImageView weatheropen;
    private static ImageView messageboardopen;
    private static ImageView newleftopen;
    private static LinearLayout openchoosecity;//打开城市设置
    private WeatherInfo weatherInfo;//传输使用
    //留言板
    private RecyclerView l_message;//留言
    private messageAadapter m_messageAadapter;
   // private List<message> m_message = new ArrayList<>();
    //新闻
    private RecyclerView l_news;//留言
    private NewAdpter m_NewAdpter;
    private List<newsbean> m_news = new ArrayList<>();
    private NewsSummaryLab mNewsSummaryLab = NewsSummaryLab.getInstance();

    //app
    private List<MyAppInfo> get_app = new ArrayList<>();

    public static final int WHAT_SET_NEWS_SUMMARY_LIST = 1;
    public static final int WHAT_GET_NEWS_SUMMARY_FAIL = 2;

    private Handler mHandlernew = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_SET_NEWS_SUMMARY_LIST:
                    int most_coun = 0;
                    ArrayList<NewsSummary> summaryArrayList = (ArrayList<NewsSummary>) msg.obj;
                    for (NewsSummary newsSummary : summaryArrayList) {
                        mNewsSummaryLab.addNewsSummary(newsSummary);
                        m_news.add(new newsbean(0,0,newsSummary.getTitle()));
//                        Log.d("lhb",newsSummary.getHref());
                        if(most_coun < 10) {
                            new Thread(new NewsSpiderThread(newsSummary, mHandler_n)).start();
                        }
                        most_coun++;
                    }
                    EventBus.getDefault().post(summaryArrayList.get(0));
                    m_NewAdpter.notifyDataSetChanged();
                    break;
                case WHAT_GET_NEWS_SUMMARY_FAIL:
                    break;
            }
        }
    };

    public static final int WHAT_SET_NEWS = 1;
    public static final int WHAT_GET_NEWS_FAIL = 2;
    public static final int WHAT_PARSING_NEWS_FAIL = 3;
    public static List<bignewsbean> m_news_m = new ArrayList<>();

    private Handler mHandler_n = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_SET_NEWS:
                    BigNews news = (BigNews) msg.obj;
//                    Log.d("lhb",news.toString());
                    m_news_m.add(new bignewsbean(0,0,news.toString(),news.toString(),news.getUrl()));
                    break;
                case WHAT_GET_NEWS_FAIL:
                    //Toast.makeText(NewsActivity.this, "获取新闻详情失败，请检查网络", Toast.LENGTH_SHORT).show();
                    //finish();
                    break;
                case WHAT_PARSING_NEWS_FAIL:
                    //Toast.makeText(NewsActivity.this, "解析新闻失败，这条新闻可能不是网易本站新闻", Toast.LENGTH_SHORT).show();
                    //finish();
                    break;
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<message> event) {
        m_message = event;
        Message message=new Message();
        message.what=0;
        handler.sendMessage(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(City event) {
        Message message=new Message();
        message.obj = event;
        message.what=1;
        handler.sendMessage(message);
    }


    //获取天气数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Butcmd event) {
        switch (event.getCmd()) {
            case "weatherget":{
                if(weatherInfo != null) {
                    EventBus.getDefault().post(weatherInfo);
                }
            }
            break;
            case "newsnfget":{
                if(m_news_m != null) {
                    Message message=new Message();
                    message.what=2;
                    handler.sendMessage(message);
                }
            }
            break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void AddApp() {
        final Dialog bottomDialog = new Dialog(getActivity(), R.style.BottomDialog);
        final View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_add_app, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(getActivity(), 16f);
        params.bottomMargin = DensityUtil.dp2px(getActivity(), 8f);
        contentView.setLayoutParams(params);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        //绑定内部控件
        final RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.dialog_add_app_list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        final List<MyAppInfoch> app_list = new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                super.run();
                //扫描得到APP列表
                get_app = ApkTool.scanLocalInstallAppList(getActivity().getPackageManager(),getActivity());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for(MyAppInfo tem: get_app) {
                            app_list.add(new MyAppInfoch(tem.getImage(),tem.getAppName(),tem.getPackname(),false));
                        }
                        MyAadapter myAadapter = new MyAadapter(getActivity(),app_list);
                        recyclerView.setAdapter(myAadapter);
                    }
                });
            }
        }.start();
        Button enter = (Button) contentView.findViewById(R.id.dialog_add_app_enter);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_appInfos.clear();
                get_app.clear();
                for (MyAppInfoch tep:app_list) {
                    if(tep.isCheck()) {
                        get_app.add(new MyAppInfo(tep.getImage(), tep.getAppName(), tep.getPackname()));
                    }
                }
                app_appadapter = new AppAdapter(getActivity(),get_app);
                l_app_list.setAdapter(app_appadapter);
                app_appadapter.setAction(new AppAdapter.Chickaction() {
                    @Override
                    public void onData(String url) {
                        AppUtil.startAPP(getActivity(),url);
                    }
                });

                s_appInfos.clear();

                for (MyAppInfo tep:get_app) {
                    s_appInfos.add(new app_save(tep.getPackname()) );
                }
                EventBus.getDefault().post(new Butcmd("updatasave"));
                bottomDialog.dismiss();
            }
        });
        bottomDialog.show();
    }

    public class MyAadapter extends RecyclerView.Adapter{
        private LayoutInflater inflater;
        private List<MyAppInfoch> list;

        public MyAadapter(Context context , List<MyAppInfoch> list) {
            this.list = list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.item_app_choose,parent,false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textview.setText(list.get(position).getPackname());
            viewHolder.choice.setChecked(list.get(position).isCheck());
            viewHolder.img.setImageDrawable(list.get(position).getImage());
            viewHolder.choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    list.get(position).setCheck(b);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder{

            private TextView textview;
            private CheckBox choice;
            private ImageView img;

            public ViewHolder(View itemView) {
                super(itemView);
                textview = (TextView) itemView.findViewById(R.id.item_app_choose_name);
                choice = (CheckBox) itemView.findViewById(R.id.item_app_choose_check);
                img = itemView.findViewById(R.id.item_app_choose_img);
            }
        }
    }

    //第三方应用
    private RecyclerView l_app_list;
    private AppAdapter app_appadapter;
    private Handler mHandler = new Handler();
    //private List<MyAppInfo> appInfos = new ArrayList<>();
    private Button app_add;

    //设置
    private Button but_setting;
    private Button but_photo;
    private Button but_system;

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

    private int getIconResId(String weather) {
        int resId;
        switch (weather) {
            case "晴":
                resId = R.mipmap.home_icon_sunny_72;
                break;
            case "阴":
                resId = R.mipmap.home_icon_cloudy_72;
                break;
            case "多云":
                resId = R.mipmap.home_icon_overcast_72;
                break;
            case "雾":
                resId = R.mipmap.home_icon_fog_72;
                break;
            case "雨":
                resId = R.mipmap.home_icon_rain_72;
                break;
            case "小雨":
                resId = R.mipmap.home_icon_rain_72;
                break;
            case "小雪":
                resId = R.mipmap.home_icon_heavysnow_72;
                break;
            case "中雨":
                resId = R.mipmap.home_icon_rain_72;
                break;
            case "阵雨":
                resId = R.mipmap.home_icon_shower_72;
                break;
            case "雷阵雨":
                resId = R.mipmap.home_icon_thunderstorm_72;
                break;
            case "阵雪":
                resId = R.mipmap.home_icon_snowshower_72;
                break;
            case "中雪":
                resId = R.mipmap.home_icon_sleet_72;
                break;
            case "大雪":
                resId = R.mipmap.home_icon_snow_72;
                break;
            case "雨夹雪":
                resId = R.mipmap.home_icon_sleet_72;
                break;
            case "霾":
                resId = R.mipmap.home_icon_fog_72;
                break;
            case "雷":
                resId = R.mipmap.home_icon_thunderstorm_72;
                break;
            default:
                resId = R.mipmap.home_icon_sunny_72;
                break;
        }
        return resId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mainmenu, container, false);
    }
}
