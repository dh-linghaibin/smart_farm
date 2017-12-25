package com.jt28.a6735.mirro.tool.news;

import android.os.Handler;
import android.os.Message;

import com.jt28.a6735.mirro.fragment.mainmenu;

import java.io.IOException;
import java.util.ArrayList;

public class NewsSummaryListSpiderThread implements Runnable {
    private Handler mHandler;

    public NewsSummaryListSpiderThread(Handler handler) {
        mHandler = handler;
    }

    @Override
    public void run() {
        try {
            ArrayList<NewsSummary> newsSummaryList = NetEaseNewsRankSpider.getNewsSummaryList();
            Message message = Message.obtain(mHandler, mainmenu.WHAT_SET_NEWS_SUMMARY_LIST);
            message.obj = newsSummaryList;
            message.sendToTarget();
        } catch (IOException e) {
            Message message = Message.obtain(mHandler, mainmenu.WHAT_GET_NEWS_SUMMARY_FAIL);
            message.sendToTarget();
            e.printStackTrace();
        }
    }
}
