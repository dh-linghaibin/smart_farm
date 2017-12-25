package com.jt28.a6735.mirro.Msg;

/**
 * Created by a6735 on 2017/9/12.
 */

public class TackingMsg {
    private String cmd;
    public TackingMsg(String cmd){
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
