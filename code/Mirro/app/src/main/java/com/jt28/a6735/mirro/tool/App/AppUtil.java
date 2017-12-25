package com.jt28.a6735.mirro.tool.App;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by a6735 on 2017/9/4.
 */

public class AppUtil {
    /*
   * 启动一个app
   */
    public static void startAPP(Context context,String appPackageName){
        try{
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
            context.startActivity(intent);
        }catch(Exception e){
            Toast.makeText(context, "没有安装", Toast.LENGTH_LONG).show();
        }
    }

}
