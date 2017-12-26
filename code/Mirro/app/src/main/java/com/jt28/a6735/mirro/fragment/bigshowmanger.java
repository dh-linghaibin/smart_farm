package com.jt28.a6735.mirro.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jt28.a6735.mirro.Msg.Butcmd;
import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.movelayout.util.SharedPreferencesUtil;
import com.jt28.a6735.viewpager.MyFragmentPagerAdapter;
import com.jt28.a6735.viewpager.MyViewPager;
import com.jt28.a6735.viewpager.PageFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by a6735 on 2017/9/20.
 */
public class bigshowmanger extends Fragment implements View.OnClickListener {
    private static int page = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initButton();
        intiFragment();
    }

    private void initButton() {
        close2 = getActivity().findViewById(R.id.bigshowmanger_leftbtn);
        close2.setOnClickListener(this);
        close = getActivity().findViewById(R.id.bigshowmanger_leftbtn2);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bigshowmanger_leftbtn:{
                if(page == 4) {
                    viewPager.setCurrentItem(2);
                    page = 2;
                } else {
                    EventBus.getDefault().post(new Butcmd("close"));
                }
            }
            break;
            case R.id.bigshowmanger_leftbtn2:{
                if(page == 4) {
                    viewPager.setCurrentItem(2);
                    page = 2;
                } else {
                    EventBus.getDefault().post(new Butcmd("close"));
                }
            }
            break;
            default:
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
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
    };

    //Fragment view 初始化
    private void intiFragment() {
        ArrayList<PageFragment> listFragment = new ArrayList<PageFragment>();
        listFragment.add(new choosecity());
        listFragment.add(new Newsfr());
        listFragment.add(new weather());
        listFragment.add(new messageboard());
        listFragment.add(new taobao());

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), listFragment);
        viewPager = (MyViewPager) getActivity().findViewById(R.id.bigshowmanger_viewpager);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setCurrentItem(0);
        viewPager.setScrollble(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Butcmd event) {
        /* Do something */
        switch (event.getCmd()) {
            case "choosecity":{
                viewPager.setCurrentItem(0);
                page = 0;
            }
                break;
            case "weatheropen":{
                viewPager.setCurrentItem(2);
                page = 2;
            }
                break;
            case "messageboardopen":{
                viewPager.setCurrentItem(3);
                page = 3;
            }
                break;
            case "newsclose":{
                viewPager.setCurrentItem(1);
                page = 1;
            }
                break;
            case "dy":{
                viewPager.setCurrentItem(4);
                page = 4;
            }
                break;
            case "ym":{
                viewPager.setCurrentItem(4);
                page = 4;
            }
                break;
            case "lw":{
                viewPager.setCurrentItem(4);
                page = 4;
            }
                break;
            case "mz":{
                viewPager.setCurrentItem(4);
                page = 4;
            }
                break;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String xy = (String) SharedPreferencesUtil.getData(getActivity(), "one", "0");
                Log.d("lhb",xy);
                if (!xy.equals("0")) {
                    String[] xys = xy.split("#");
                    if (xys.length == 2) {
                        if(Integer.parseInt(xys[0]) < 820) {
                            close.setVisibility(View.GONE);
                            close2.setVisibility(View.VISIBLE);
                        } else {
                            close.setVisibility(View.VISIBLE);
                            close2.setVisibility(View.GONE);
                        }
                    }
                    Log.d("lhb", Arrays.toString(xys));
                } else {
                    close.setVisibility(View.VISIBLE);
                    close2.setVisibility(View.GONE);
                }
            }
        }).run();
    };

    private MyViewPager viewPager;
    private Button close;
    private Button close2;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bigshowmanger, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //TODO now it's visible to user
        }
    }
}
