package com.jt28.a6735.mirro.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.view.NewsAdpter.BigNewAdpter;
import com.jt28.a6735.mirro.view.NewsAdpter.bignewsbean;
import com.jt28.a6735.viewpager.PageFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.jt28.a6735.mirro.fragment.mainmenu.m_news_m;

/**
 * Created by a6735 on 2017/9/12.
 */

public class Newsfr extends PageFragment implements View.OnClickListener{

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //m_news.add(new bignewsbean(0,0,"大飒飒地方拉到凤凰路口见阿斯顿好了看","","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1506412234&di=f54308ed637362d82f5b3deef94e631d&imgtype=jpg&er=1&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201605%2F11%2F170522t9d05760qwnwyw0i.jpg"));

        l_news = getActivity().findViewById(R.id.newsfr_list);
        l_news.setItemAnimator(new DefaultItemAnimator());
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        l_news.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mBigNewAdpter = new BigNewAdpter(getActivity(), m_news, new BigNewAdpter.Completecallback() {
            @Override
            public void Autoreadcomplete(int pos) {

            }
            @Override
            public void chinck(int bit, int pos) {
                l_news.smoothScrollToPosition(pos);
            }
        });
        l_news.setHasFixedSize(true);
        l_news.setAdapter(mBigNewAdpter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    //ui线程
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //m_news = (List<bignewsbean>) msg.obj;
                    //mBigNewAdpter.notifyDataSetChanged();
                    //NewsSummary summaryArrayList = (NewsSummary) msg.obj;
                    //new Thread(new NewsSpiderThread(summaryArrayList, mHandler)).start();
                    break;
                case 1:
                    //EventBus.getDefault().post(new Butcmd("newsnfget"));
                    m_news.clear();
                    for(bignewsbean tmp:m_news_m) {
                        if(tmp.getContent().length() > 200) {
                            String xx = tmp.getContent().substring(0, 200);
                            tmp.setContent(xx);
                            m_news.add(tmp);
                        } else {
                            m_news.add(tmp);
                        }
                        //m_news.add(tmp);
                        Log.d("lhb",tmp.toString());
                    }
                    //m_news = m_news_m;
                    mBigNewAdpter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

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
        Message message=new Message();
        message.what=1;
        handler.sendMessage(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<bignewsbean> event) {
        Message message=new Message();
        message.obj = event;
        message.what=0;
        handler.sendMessage(message);
    }

    private RecyclerView l_news;
    private BigNewAdpter mBigNewAdpter;
    List<bignewsbean> m_news = new ArrayList<>();

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
        return inflater.inflate(R.layout.news, container, false);
    }
}
