package com.jt28.a6735.smart_farm.tool;

import android.os.Message;

/**
 * Created by a6735 on 2017/5/26.
 */

public class Tool {
    public static void SendMsg(android.os.Handler handler, int msg) {
        Message message=new Message();
        message.what=msg;
        handler.sendMessage(message);
    }
}
