package com.jt28.a6735.mirro.tool.news;

import android.os.Handler;
import android.os.Message;

import com.jt28.a6735.mirro.fragment.mainmenu;

import java.io.IOException;

public class NewsSpiderThread implements Runnable {
    private NewsSummary mNewsSummary;
    private Handler mHandler;

    public NewsSpiderThread(NewsSummary newsSummary, Handler handler) {
        mNewsSummary = newsSummary;
        mHandler = handler;
    }

    @Override
    public void run() {
        try {
            BigNews news = NetEaseNewsRankSpider.getNewsFromNewsSummary(mNewsSummary);
            Message message = Message.obtain(mHandler, mainmenu.WHAT_SET_NEWS);
            message.obj = news;
            message.sendToTarget();
        } catch (IOException e) {
            Message message = Message.obtain(mHandler, mainmenu.WHAT_GET_NEWS_FAIL);
            message.sendToTarget();
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            Message message = Message.obtain(mHandler, mainmenu.WHAT_PARSING_NEWS_FAIL);
            message.sendToTarget();
            e.printStackTrace();
        }
    }
}
