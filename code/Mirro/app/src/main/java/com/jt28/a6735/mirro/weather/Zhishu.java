package com.jt28.a6735.mirro.weather;

/**
 * Created by a6735 on 2017/7/14.
 */

public class Zhishu {
    private String areaid;
    private String name;
    private String level;
    private String text;
    private String detail;

    public Zhishu() {
    }

    public Zhishu(String areaid, String name, String level, String text, String detail) {
        this.areaid = areaid;
        this.name = name;
        this.level = level;
        this.text = text;
        this.detail = detail;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
