package com.jt28.a6735.mirro.view.NewsAdpter;

/**
 * Created by a6735 on 2017/9/11.
 */

public class bignewsbean {
    private int Buttonstatus;
    private int pro;//进度
    private String content;//内容
    private String tal;//抬头
    private String url;//图片的地址

    public bignewsbean(int Buttonstatus, int pro, String content,String tal,String url){
        this.Buttonstatus = Buttonstatus;
        this.pro = pro;
        this.content = content;
        this.tal = tal;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTal() {
        return tal;
    }

    public String getUrl() {
        return url;
    }

    public void setTal(String tal) {
        this.tal = tal;
    }

    public int getButtonstatus() {
        return Buttonstatus;
    }

    public int getPro() {
        return pro;
    }

    public String getContent() {
        return content;
    }

    public void setButtonstatus(int buttonstatus) {
        Buttonstatus = buttonstatus;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPro(int pro) {
        this.pro = pro;
    }
}
