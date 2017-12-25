package com.jt28.a6735.mirro.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jt28.a6735.mirro.Msg.Butcmd;
import com.jt28.a6735.mirro.R;
import com.jt28.a6735.viewpager.PageFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by a6735 on 2017/12/20.
 */

public class taobao extends PageFragment implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        WebView webView = (WebView)getActivity().findViewById(R.id.taobao_web);
//        webView.loadUrl("https://www.taobao.com/markets/nvzhuang/taobaonvzhuang?spm=a21bo.2017.201867-links-0.1.476bbb5cH18ner");
//        //允许JS执行
//        //支持javascript
//        webView.getSettings().setJavaScriptEnabled(true);
//        // 设置可以支持缩放
//        webView.getSettings().setSupportZoom(true);
//        // 设置出现缩放工具
//        webView.getSettings().setBuiltInZoomControls(true);
//        //扩大比例的缩放
//        webView.getSettings().setUseWideViewPort(true);
//        //自适应屏幕
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setAppCacheMaxSize(1024*1024*8);
//        String appCachePath = getActivity().getCacheDir().getAbsolutePath();
//        webView.getSettings().setAppCachePath(appCachePath);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setAppCacheEnabled(true);
        initView();
    }
    private String taobao_url = "https://www.taobao.com/markets/nvzhuang/taobaonvzhuang?spm=a21bo.2017.201867-links-0.1.476bbb5cH18ner";
    private WebView webView;
    private void initView(){
        webView = (WebView) getActivity().findViewById(R.id.taobao_web);
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);

        //如果不设置WebViewClient，请求会跳转系统浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if (url.toString().contains("sina.cn")){
                    view.loadUrl(url);
                    return true;
                }
                return false;
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request.getUrl().toString().contains("sina.cn")){
                        view.loadUrl(taobao_url);
                        return true;
                    }
                }
                return false;
            }

        });
//        webView.loadUrl(taobao_url);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Butcmd event) {
        /* Do something */
        switch (event.getCmd()) {
            case "dy": {
                taobao_url = "https://www.taobao.com/markets/nvzhuang/taobaonvzhuang?spm=a21bo.2017.201867-links-0.1.476bbb5cH18ner";
                webView.loadUrl(taobao_url);
            }
            break;
            case "ym": {
                taobao_url = "http://www.dongsport.com/";
                webView.loadUrl(taobao_url);
            }
            break;
            case "lw": {
                taobao_url = "https://search.jd.com/Search?keyword=%E6%96%B0%E9%A3%8E%E6%BB%A4%E7%BD%91&enc=utf-8&wq=%E6%96%B0%E9%A3%8E%E6%BB%A4%E7%BD%91&pvid=36d01c3be29649ac8f8526b00e110790";
                webView.loadUrl(taobao_url);
            }
            break;
            case "mz": {
                taobao_url = "http://www.jumei.com/";
                webView.loadUrl(taobao_url);
            }
            break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.taobao, container, false);
    }
}
