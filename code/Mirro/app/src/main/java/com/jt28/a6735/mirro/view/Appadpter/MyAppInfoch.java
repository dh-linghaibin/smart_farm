package com.jt28.a6735.mirro.view.Appadpter;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by gray_dog3 on 16/3/3.
 */
public class MyAppInfoch implements Serializable {
    private Drawable image;
    private String appName;
    private String packname;
    private boolean check;

    public MyAppInfoch(Drawable image, String appName, String packname,boolean check) {
        this.image = image;
        this.appName = appName;
        this.packname = packname;
        this.check = check;
    }
    public MyAppInfoch() {

    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
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
