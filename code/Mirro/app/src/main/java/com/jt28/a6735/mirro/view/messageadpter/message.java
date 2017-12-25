package com.jt28.a6735.mirro.view.messageadpter;

import java.io.Serializable;

/**
 * Created by a6735 on 2017/8/30.
 */

public class message implements Serializable {
    private int type;//消息类型
    private boolean read;//是否读过
    private String path;//语音 图片 视屏的 路径
    private String content;//显示内容
    private String newtime;//保存时间
    private long longtime;//声音长度
    public message(int type,boolean read,String newtime,String path,String content,long longtime){
        this.type = type;
        this.read = read;
        this.newtime = newtime;
        this.path = path;
        this.content = content;
        this.longtime = longtime;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isRead() {
        return read;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public String getContent() {
        return content;
    }

    public long getLongtime() {
        return longtime;
    }

    public String getNewtime() {
        return newtime;
    }

    public void setLongtime(long longtime) {
        this.longtime = longtime;
    }

    public void setNewtime(String newtime) {
        this.newtime = newtime;
    }
}
