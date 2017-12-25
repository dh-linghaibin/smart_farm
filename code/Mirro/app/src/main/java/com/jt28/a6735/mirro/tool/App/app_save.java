package com.jt28.a6735.mirro.tool.App;

import java.io.Serializable;

/**
 * Created by gray_dog3 on 16/3/3.
 */
public class app_save implements Serializable {
    private String packname;

    public app_save(String packname) {
        this.packname = packname;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }
}
