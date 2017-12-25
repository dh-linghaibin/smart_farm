package com.jt28.a6735.mirro.weather;

/**
 * Created by a6735 on 2017/7/14.
 */

public class UseArea {
    private String areaid;
    private String areaid2345;
    private String areaName;
    private Boolean main;

    public UseArea() {
    }

    public UseArea(String areaid, String areaid2345, String areaName, Boolean main) {
        this.areaid = areaid;
        this.areaid2345 = areaid2345;
        this.areaName = areaName;
        this.main = main;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaid2345() {
        return areaid2345;
    }

    public void setAreaid2345(String areaid2345) {
        this.areaid2345 = areaid2345;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Boolean getMain() {
        return main;
    }

    public void setMain(Boolean main) {
        this.main = main;
    }
}
