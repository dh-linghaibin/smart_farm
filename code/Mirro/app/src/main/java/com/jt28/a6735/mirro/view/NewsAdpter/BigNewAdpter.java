package com.jt28.a6735.mirro.view.NewsAdpter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.view.HorizontalNumProgressBar;

import java.util.List;

/**
 * Created by a6735 on 2017/9/4.
 */

public class BigNewAdpter extends  RecyclerView.Adapter  {
    private LayoutInflater inflater;
    private List<bignewsbean> listx;
    private Context context;
    //1.创建 SpeechSynthesizer 对象, 第二个参数： 本地合成时传 InitListener
    private SpeechSynthesizer mTts;
    private Completecallback callback;

    //设置监听
    public void setAction(Completecallback action) {
        this.callback = action;
    }

    public BigNewAdpter(Context context , List<bignewsbean> list, Completecallback action) {
        this.listx = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.callback = action;

        mTts= SpeechSynthesizer.createSynthesizer(context, null);
        //2.合成参数设置，详见《 MSC Reference Manual》 SpeechSynthesizer 类
        //设置发音人（更多在线发音人，用户可参见 附录13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan"); //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "40");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_bignewsadpter,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final bignewsbean myAppInfo = listx.get(position);
        Resources resources = context.getResources();


//        try {
//            bitmap = GetResources.getBitmap();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    Bitmap bitmap = null;
//                    //创建一个url对象
//                    URL url=new URL(myAppInfo.getUrl());
//                    //打开URL对应的资源输入流
//                    InputStream is= url.openStream();
//                    //从InputStream流中解析出图片
//                    bitmap = BitmapFactory.decodeStream(is);
//                    //  imageview.setImageBitmap(bitmap);
//                    //发送消息，通知UI组件显示图片
//                    viewHolder.img.setImageBitmap(bitmap);
//                    //关闭输入流
//                    is.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
        viewHolder.content.setMovementMethod(new ScrollingMovementMethod());
        viewHolder.content.setText(myAppInfo.getContent());

        if(myAppInfo.getButtonstatus() == 0 ) {
            viewHolder.voiceprogress.setVisibility(View.GONE);
            Drawable imageDrawable = resources.getDrawable(R.mipmap.btn_broadcast_default);
            viewHolder.but.setBackgroundDrawable(imageDrawable);
            viewHolder.but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG","数据按下");
                    listx.get(position).setButtonstatus(1);
                    callback.chinck(0,position);
                    notifyItemRangeChanged(position,2);
                }
            });
        } else {
            viewHolder.voiceprogress.setVisibility(View.VISIBLE);
            Drawable imageDrawable = resources.getDrawable(R.mipmap.btn_broadcast_pressed);
            viewHolder.but.setBackgroundDrawable(imageDrawable);
            //3.开始合成
            mTts.startSpeaking(myAppInfo.getContent(), new SynthesizerListener() {
                @Override
                public void onSpeakBegin() {
                    Log.d("lhb","onSpeakBegin！！！");
                }
                @Override
                public void onBufferProgress(int i, int i1, int i2, String s) {
                    Log.d("lhb","onBufferProgress！！！" + i +"  " + i1 + "  " + i2 + s);
                }

                @Override
                public void onSpeakPaused() {
                    Log.d("lhb","onSpeakPaused！！！");
                }

                @Override
                public void onSpeakResumed() {
                    Log.d("lhb","onSpeakResumed！！！");
                }

                @Override
                public void onSpeakProgress(int i, int i1, int i2) {
                    Log.d("lhb","onSpeakProgress！！！" + i +"  " + i1 + "  " + i2);
                    viewHolder.voiceprogress.setProgress(i);
                    myAppInfo.setPro(i);
                }

                @Override
                public void onCompleted(SpeechError speechError) {
                    Log.d("lhb","播放完成！！！" + speechError);
                    //播放结束
                    Message handleMsg = new Message();
                    handleMsg.what = 1;
                    handleMsg.arg1 = position;
                    updateChatHandler.sendMessage(handleMsg);
                }

                @Override
                public void onEvent(int i, int i1, int i2, Bundle bundle) {
                    Log.d("lhb","onEvent！！！" + i +"  " + i1 + "  " + i2);
                }
            });
            viewHolder.but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TAG","数据按下");
                    mTts.stopSpeaking();
                    listx.get(position).setButtonstatus(0);
                    callback.chinck(0,position);
                    notifyItemRangeChanged(position,2);
                }
            });
        }
    }

    private Handler updateChatHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    listx.get(msg.arg1).setButtonstatus(0);
                    callback.chinck(0,msg.arg1);
                    notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public int getItemCount() {
        return listx.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
//        private ImageView img;
        private TextView content;
        private Button but;
        private HorizontalNumProgressBar voiceprogress;

        public ViewHolder(View itemView) {
            super(itemView);
//            img = itemView.findViewById(R.id.item_bignewsadpter_img);
            content = itemView.findViewById(R.id.item_bignewsadpter_content);
            voiceprogress = itemView.findViewById(R.id.item_bignewsadpter_voiceprogress);
            but = itemView.findViewById(R.id.item_bignewsadpter_but);
        }
    }

    public interface Completecallback{
        void Autoreadcomplete(int pos);
        void chinck(int bit,int pos);
    }
}
