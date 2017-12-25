package com.jt28.a6735.mirro.movelayout.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

/**
 * Created by loonggg on 2017/3/20.
 */

public class SharedPreferencesUtil {
    //存储的sharedpreferences文件名
    public static String FILE_NAME = "setting";
    public static String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**
     * 保存数据到文件
     *
     * @param context
     * @param key
     * @param data
     */
    public static void saveData(Context context, String key, Object data) {
        SharedPreferences mySharedPreferences= context.getSharedPreferences("userdata",
        Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putString(key, (String) data);
        //提交当前数据
        editor.apply();
    }

    /**
     * 从文件中读取数据
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Object getData(Context context, String key, Object defValue) {
        SharedPreferences sharedPreferences= context.getSharedPreferences("userdata",
                Activity.MODE_PRIVATE);
        String userName =sharedPreferences.getString(key, "");
        return userName;
    }

    /**
     * 初始化数据到文件
     * 有就跳过，没有就新增
     *
     * @param context
     * @param key
     * @param data
     */
    public static void initialData(Context context, String key, Object data) {
        try {
            if (getData(context, key, "HasNoInitial").toString().equals("HasNoInitial")) {
                saveData(context, key, data);
            }
        } catch (Exception e) {
            //CommFunc.ToastPromptMsg("XML配置文件初始化操作失败");
        }
    }
}
