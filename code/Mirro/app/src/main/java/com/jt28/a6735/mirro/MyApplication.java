package com.jt28.a6735.mirro;

import android.app.Application;
import android.util.Log;

import com.jt28.a6735.mirro.tool.AssetsCopyUtil;


/**
 * Created by ghbha on 2016/5/14.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AssetsCopyUtil.copyEmbassy2Databases(this, "data/data/" + this.getPackageName() + "/databases/",
                "location.db");
        Log.d("MyApplication","启动了");
    }
}
