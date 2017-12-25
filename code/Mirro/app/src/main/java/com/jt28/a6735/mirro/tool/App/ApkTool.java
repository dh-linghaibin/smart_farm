package com.jt28.a6735.mirro.tool.App;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gray_dog3 on 16/3/3.
 * 扫描本地安装的应用,工具类
 */
public class ApkTool {
    static String TAG = "ApkTool";
    public static List<MyAppInfo> mLocalInstallApps = null;

    public static List<MyAppInfo> scanLocalInstallAppList(PackageManager packageManager, Activity mContext) {
        List<MyAppInfo> myAppInfos = new ArrayList<MyAppInfo>();
        try {
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                //过滤掉系统app
                if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                    continue;
                }
                if("com.jt28.a6735.mirro".equals(packageInfo.packageName)) {
                    continue;
                }
                Log.d("lhb",packageInfo.packageName);
                MyAppInfo myAppInfo = new MyAppInfo(null,null,null);
                myAppInfo.setAppName("测试");//(mContext.getResources().getString(packageInfo.applicationInfo.labelRes));
                myAppInfo.setPackname(packageInfo.packageName);
                if (packageInfo.applicationInfo.loadIcon(packageManager) == null) {
                    continue;
                }
                myAppInfo.setImage(packageInfo.applicationInfo.loadIcon(packageManager));
                myAppInfos.add(myAppInfo);
            }
        } catch (Exception e){
            Log.e(TAG,"===============获取应用包信息失败");
        }
        return myAppInfos;
    }

}
