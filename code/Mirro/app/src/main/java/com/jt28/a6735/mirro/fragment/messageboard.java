package com.jt28.a6735.mirro.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jt28.a6735.mirro.Msg.Butcmd;
import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.tool.Voice.VoiceManager;
import com.jt28.a6735.mirro.view.CustomProgressbar;
import com.jt28.a6735.mirro.view.PaletteView;
import com.jt28.a6735.mirro.view.bigmessageadpter.bigmessageAadapter;
import com.jt28.a6735.mirro.view.messageadpter.message;
import com.jt28.a6735.viewpager.PageFragment;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jt28.a6735.mirro.MainActivity.m_message;

/**
 * Created by a6735 on 2017/8/25.
 */

public class messageboard extends PageFragment implements View.OnClickListener{
    private LinearLayout drawingboard;
    private PaletteView mPaletteView;
    private ImageView earser;//橡皮擦
    private LinearLayout color_ly1,color_ly2,color_ly3,color_ly4,color_ly5,color_ly6;//画板颜色选择
    private LinearLayout pen1,pen2,pen3;//笔的粗细
    private Button voice,write,video;//功能选择

    private RecyclerView Rl_message;//留言
    private bigmessageAadapter m_messageAadapter;
//    private List<message> m_message = new ArrayList<>();
    private int function = 0;//0-语音 1-画画 2-视屏

    //录音
    private VoiceManager voiceManager;
    private ImageView voicedb;
    private TextView voicelongtime;
    private CustomProgressbar pb_button;
    private LinearLayout messagegroup;
    private LinearLayout voicedbgroup;
    private String filePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messageboard, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        drawingboard = getActivity().findViewById(R.id.messageboard_drawingboard_lay);
        mPaletteView = getActivity().findViewById(R.id.messageboard_drawingboard);
        earser = getActivity().findViewById(R.id.messageboard_earser);

        color_ly1 = getActivity().findViewById(R.id.messageboard_color_lay1);
        color_ly1.setOnClickListener(this);
        color_ly2 = getActivity().findViewById(R.id.messageboard_color_lay2);
        color_ly2.setOnClickListener(this);
        color_ly3 = getActivity().findViewById(R.id.messageboard_color_lay3);
        color_ly3.setOnClickListener(this);
        color_ly4 = getActivity().findViewById(R.id.messageboard_color_lay4);
        color_ly4.setOnClickListener(this);
        color_ly5 = getActivity().findViewById(R.id.messageboard_color_lay5);
        color_ly5.setOnClickListener(this);
        color_ly6 = getActivity().findViewById(R.id.messageboard_color_lay6);
        color_ly6.setOnClickListener(this);
        color_ly1.setSelected(true);//第一次初始化的颜色

        pen1 = getActivity().findViewById(R.id.messageboard_pen1);
        pen1.setOnClickListener(this);
        pen2 = getActivity().findViewById(R.id.messageboard_pen2);
        pen2.setOnClickListener(this);
        pen3 = getActivity().findViewById(R.id.messageboard_pen3);
        pen3.setOnClickListener(this);
        pen1.setSelected(true);//第一次初始化笔的粗细

        voice = getActivity().findViewById(R.id.messageboard_voice);
        voice.setOnClickListener(this);
        write = getActivity().findViewById(R.id.messageboard_write);
        write.setOnClickListener(this);
        video = getActivity().findViewById(R.id.messageboard_video);
        video.setOnClickListener(this);

        mPaletteView.setSelected(false);
        mPaletteView.setPenRawSize(2);
        mPaletteView.setPenColor(0xff000000);//设置笔的颜色

        //录音
        filePath = Environment.getExternalStorageDirectory().getPath()+"/VoiceManager/audio";

        messagegroup = getActivity().findViewById(R.id.messagedboard_messagegroup);
        voicedbgroup = getActivity().findViewById(R.id.messagedboard_voicedbgroup);
        voicelongtime = getActivity().findViewById(R.id.messagedboard_voicelongtime);

        pb_button = getActivity().findViewById(R.id.messagedboard_voicestart);
        voicedb = getActivity().findViewById(R.id.messagedboard_voicedb);
        voiceManager =VoiceManager.getInstance(getActivity());
//        voiceManager.startVoiceRecord(Environment.getExternalStorageDirectory().getPath() + "/VoiceManager/audio");
        voiceManager.setVoiceRecordListener(new VoiceManager.VoiceRecordCallBack() {
            @Override
            public void recDoing(long time, String strTime) {
                Message message=new Message();
                message.what=2;
                message.obj = strTime;
                handler.sendMessage(message);
            }
            @Override
            public void recVoiceGrade(int grade) {
                Message message=new Message();
                message.what=3;
                message.obj = grade;
                handler.sendMessage(message);
            }
            @Override
            public void recStart(boolean init) {

            }
            @Override
            public void recPause(String str) {

            }
            @Override
            public void recFinish(long length, String strLength, String path) {
                lpath = path;
                llength = length;
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        });
        pb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (function) {
                    case 0:{//语音留言
                        if(voice_but) {
                            Message message=new Message();
                            message.what=4;
                            handler.sendMessage(message);
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                }
//                            }).run();
                        }
                    }
                        break;
                    case 1:{//图像留言
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Bitmap bm = mPaletteView.buildBitmap();
                                String savedFile = saveImage(bm, 100);
                                if (savedFile != null) {
                                    lsavedFile = savedFile;
                                    Message message=new Message();
                                    message.what=0;
                                    handler.sendMessage(message);
                                }else{
                                }
                            }
                        }).start();
                    }
                        break;
                    case 2:{//视屏留言

                    }
                        break;
                }

            }
        });

        earser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(earser.isSelected()) {
                    earser.setSelected(false);
                } else {
                    earser.setSelected(true);
                }
                mPaletteView.clear();
            }
        });

        Rl_message = getActivity().findViewById(R.id.messagedboard_message);
        Rl_message.setItemAnimator(new DefaultItemAnimator());
        Rl_message.setLayoutManager(new GridLayoutManager(getActivity(),1));
        m_messageAadapter = new bigmessageAadapter(getActivity(),m_message);
        Rl_message.setAdapter(m_messageAadapter);
    }

    private boolean voice_but = true;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.messageboard_voice:{//录音

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            drawingboard.setVisibility(View.GONE);
                            function = 0;
                            video.setTextColor(Color.parseColor("#80313131"));
                            write.setTextColor(Color.parseColor("#80313131"));
                            voice.setTextColor(Color.parseColor("#54aef7"));
                        }
                    }).run();
            }
                break;
            case R.id.messageboard_write:{//画板
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        drawingboard.setVisibility(View.VISIBLE);
                        function = 1;
                        video.setTextColor(Color.parseColor("#80313131"));
                        voice.setTextColor(Color.parseColor("#80313131"));
                        write.setTextColor(Color.parseColor("#54aef7"));
                    }
                }).run();
            }
                break;
            case R.id.messageboard_video: {//视屏
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        drawingboard.setVisibility(View.GONE);
                        function = 2;
                        voice.setTextColor(Color.parseColor("#80313131"));
                        write.setTextColor(Color.parseColor("#80313131"));
                        video.setTextColor(Color.parseColor("#54aef7"));
                    }
                }).run();
            }
                break;
            case R.id.messageboard_color_lay1:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        color_ly1.setSelected(false);
                        color_ly2.setSelected(false);
                        color_ly3.setSelected(false);
                        color_ly4.setSelected(false);
                        color_ly5.setSelected(false);
                        color_ly6.setSelected(false);

                        color_ly1.setSelected(true);
                        mPaletteView.setPenColor(0xff000000);//设置笔的颜色 黑色
                    }
                }).run();
            }
                break;
            case R.id.messageboard_color_lay2:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        color_ly1.setSelected(false);
                        color_ly2.setSelected(false);
                        color_ly3.setSelected(false);
                        color_ly4.setSelected(false);
                        color_ly5.setSelected(false);
                        color_ly6.setSelected(false);

                        color_ly2.setSelected(true);
                        mPaletteView.setPenColor(0xff808080);//设置笔的颜色 灰色
                    }
                }).run();
            }
                break;
            case R.id.messageboard_color_lay3:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        color_ly1.setSelected(false);
                        color_ly2.setSelected(false);
                        color_ly3.setSelected(false);
                        color_ly4.setSelected(false);
                        color_ly5.setSelected(false);
                        color_ly6.setSelected(false);

                        color_ly3.setSelected(true);
                        mPaletteView.setPenColor(0xffff0000);//设置笔的颜色 红色
                    }
                }).run();
            }
                break;
            case R.id.messageboard_color_lay4:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        color_ly1.setSelected(false);
                        color_ly2.setSelected(false);
                        color_ly3.setSelected(false);
                        color_ly4.setSelected(false);
                        color_ly5.setSelected(false);
                        color_ly6.setSelected(false);

                        color_ly4.setSelected(true);
                        mPaletteView.setPenColor(0xff87ceeb);//设置笔的颜色 蓝色
                    }
                }).run();
            }
                break;
            case R.id.messageboard_color_lay5:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        color_ly1.setSelected(false);
                        color_ly2.setSelected(false);
                        color_ly3.setSelected(false);
                        color_ly4.setSelected(false);
                        color_ly5.setSelected(false);
                        color_ly6.setSelected(false);

                        color_ly5.setSelected(true);
                        mPaletteView.setPenColor(0xff008000);//设置笔的颜色 绿色
                    }
                }).run();
            }
                break;
            case R.id.messageboard_color_lay6:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        color_ly1.setSelected(false);
                        color_ly2.setSelected(false);
                        color_ly3.setSelected(false);
                        color_ly4.setSelected(false);
                        color_ly5.setSelected(false);
                        color_ly6.setSelected(false);

                        color_ly6.setSelected(true);
                        mPaletteView.setPenColor(0xffffa500);//设置笔的颜色 黄色
                    }
                }).run();
            }
                break;
            case R.id.messageboard_pen1:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pen1.setSelected(false);
                        pen2.setSelected(false);
                        pen3.setSelected(false);

                        pen1.setSelected(true);
                        mPaletteView.setPenRawSize(2);
                    }
                }).run();
            }
                break;
            case R.id.messageboard_pen2:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pen1.setSelected(false);
                        pen2.setSelected(false);
                        pen3.setSelected(false);

                        pen2.setSelected(true);
                        mPaletteView.setPenRawSize(5);
                    }
                }).run();
            }
                break;
            case R.id.messageboard_pen3:{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pen1.setSelected(false);
                        pen2.setSelected(false);
                        pen3.setSelected(false);

                        pen3.setSelected(true);
                        mPaletteView.setPenRawSize(9);
                    }
                }).run();
            }
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //TODO now it's visible to user
        }
    }

    private String lsavedFile;
    private String lpath;
    private long llength;
    //ui线程
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            scanFile(getActivity(), lsavedFile);
                            Log.d("lhb","保存成功" + lsavedFile);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                            String str = formatter.format(curDate);

                            int count = 0;

                            List<message> temp_message = new ArrayList<>();
                            temp_message.add(new message(1, false,str ,lsavedFile, "图像: ",0));
                            for(message tmp: m_message) {
                                if(count < 4) {
                                    temp_message.add(tmp);
                                }
                                count++;
                            }
                            m_message = temp_message;
                            //m_message.add(new message(1, false,str ,lsavedFile, "图像: ",0));
                            //更新语音数据
                            EventBus.getDefault().post(m_message);
                            //更新
                            //m_messageAadapter.notifyDataSetChanged();
                            m_messageAadapter = new bigmessageAadapter(getActivity(),m_message);
                            Rl_message.setAdapter(m_messageAadapter);
                            //保存
                            EventBus.getDefault().post(new Butcmd("updatasave"));
                        }
                    }).run();
                    break;
                case 1:
                    //更新语音数据
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                            String str = formatter.format(curDate);
                           // m_message.add(new message(0, false,str ,lpath, "语音: ",llength));
                            int count = 0;

                            List<message> temp_message = new ArrayList<>();
                            temp_message.add(new message(0, false,str ,lpath, "语音: ",llength));
                            for(message tmp: m_message) {
                                if(count < 4) {
                                    temp_message.add(tmp);
                                }
                                count++;
                            }
                            m_message = temp_message;

                            //m_messageAadapter.notifyDataSetChanged();
                            EventBus.getDefault().post(m_message);

                            m_messageAadapter = new bigmessageAadapter(getActivity(),m_message);
                            Rl_message.setAdapter(m_messageAadapter);
                            //保存
                            EventBus.getDefault().post(new Butcmd("updatasave"));
                        }
                    }).run();
                    break;
                case 2:
                    voicelongtime.setText((String)msg.obj);
                    break;
                case 3:
                    Drawable imageDrawable;
                    if((int)msg.obj > 50) {
                        imageDrawable = getActivity().getBaseContext().getResources().getDrawable(R.mipmap.icon_volume_blue); //图片在drawable目录下
                    } else if((int)msg.obj > 45) {
                        imageDrawable = getActivity().getBaseContext().getResources().getDrawable(R.mipmap.icon_volume_bg5); //图片在drawable目录下
                    } else if((int)msg.obj > 40) {
                        imageDrawable = getActivity().getBaseContext().getResources().getDrawable(R.mipmap.icon_volume_bg4); //图片在drawable目录下
                    } else if((int)msg.obj > 35) {
                        imageDrawable = getActivity().getBaseContext().getResources().getDrawable(R.mipmap.icon_volume_bg3); //图片在drawable目录下
                    } else if((int)msg.obj > 25) {
                        imageDrawable = getActivity().getBaseContext().getResources().getDrawable(R.mipmap.icon_volume_bg2); //图片在drawable目录下
                    } else {
                        imageDrawable = getActivity().getBaseContext().getResources().getDrawable(R.mipmap.icon_volume_bg1); //图片在drawable目录下
                    }
                    voicedb.setBackgroundDrawable(imageDrawable);
                    break;
                case 4:
                    if(pb_button.isFlag()) {
                        if(voiceManager!=null){
                            voiceManager.stopVoiceRecord();
                        }
                        pb_button.setFlag(false);
                        messagegroup.setVisibility(View.VISIBLE);
                        voicedbgroup.setVisibility(View.GONE);
                        voice_but = false;
                        new Handler().postDelayed(new Runnable(){
                            public void run() {
                                //execute the task
                                voice_but = true;
                            }
                        }, 800);
                    } else {
//                        if (voiceManager != null) {
//                            voiceManager.pauseOrStartVoiceRecord();
//                        }
                        pb_button.setFlag(true);
                        voicedbgroup.setVisibility(View.VISIBLE);
                        messagegroup.setVisibility(View.GONE);
                        if(voiceManager!=null) {
                            voiceManager.startVoiceRecord(filePath);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private static void scanFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }

    private static String saveImage(Bitmap bmp, int quality) {
        if (bmp == null) {
            return null;
        }
        File appDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (appDir == null) {
            return null;
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, quality, fos);
            fos.flush();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
