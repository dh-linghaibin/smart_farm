package com.jt28.a6735.mirro.tool.App;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by gray_dog3 on 16/3/3.
 */
public class MyAppInfo implements Serializable {
    private Drawable image;
    private String appName;
    private String packname;

    public MyAppInfo(Drawable image, String appName,String packname) {
        this.image = image;
        this.appName = appName;
        this.packname = packname;
    }
    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }
}
