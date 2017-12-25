package com.jt28.a6735.mirro.view.NewsAdpter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class NewAdpter extends  RecyclerView.Adapter  {
    private LayoutInflater inflater;
    private List<newsbean> list;
    private Context context;
    //1.创建 SpeechSynthesizer 对象, 第二个参数： 本地合成时传 InitListener
    private SpeechSynthesizer mTts;
    private Completecallback callback;

    //设置监听
    public void setAction(Completecallback action) {
        this.callback = action;
    }

    public NewAdpter(Context context , List<newsbean> list,Completecallback action) {
        this.list = list;
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
        return new ViewHolder(inflater.inflate(R.layout.item_newsadpter,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final newsbean myAppInfo = list.get(position);

        viewHolder.content.setText(myAppInfo.getContent());
        if(myAppInfo.getButtonstatus() == 0 ) {
            viewHolder.butback.setVisibility(View.GONE);
            viewHolder.contentback.setBackgroundResource(0);
            viewHolder.voiceprogress.setVisibility(View.GONE);
            viewHolder.content.setTextColor(0xFF303030);
        } else {
            viewHolder.butback.setVisibility(View.VISIBLE);
            viewHolder.contentback.setBackgroundColor(0xe0cde1f1);
            viewHolder.voiceprogress.setVisibility(View.VISIBLE);
            viewHolder.content.setTextColor(0xFF42a5f6);
            //设置按钮状态
            Resources resources = context.getResources();
            if(myAppInfo.getButtonstatus() == 1 ) {
                Drawable imageDrawable = resources.getDrawable(R.mipmap.home_new_icon_play_default); //图片在drawable目录下
                viewHolder.but.setBackgroundDrawable(imageDrawable);
                //3.开始合成
                String str1 = myAppInfo.getContent();
                String str2 = str1.substring(1, str1.length());
                mTts.startSpeaking(str2, new SynthesizerListener() {
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
                        myAppInfo.setButtonstatus(0);
                        //开始播放下一下
                        list.get(position+1).setButtonstatus(1);
                        notifyItemRangeChanged(position,2);
                        if(callback != null) {
                            callback.Autoreadcomplete(position);
                        }
                    }

                    @Override
                    public void onEvent(int i, int i1, int i2, Bundle bundle) {
                        Log.d("lhb","onEvent！！！" + i +"  " + i1 + "  " + i2);
                    }
                });
                //设置监听
                viewHolder.but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mTts.stopSpeaking();
                        list.get(position).setButtonstatus(2);
                        notifyItemRangeChanged(position,2);
                    }
                });
            } else {
                Drawable imageDrawable = resources.getDrawable(R.mipmap.home_new_icon_suspend_default); //图片在drawable目录下
                viewHolder.but.setBackgroundDrawable(imageDrawable);
                viewHolder.voiceprogress.setProgress(myAppInfo.getPro());
                mTts.stopSpeaking();
                viewHolder.but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.get(position).setButtonstatus(1);
                        notifyItemRangeChanged(position,2);
                    }
                });
            }
            //viewHolder.voiceprogress.setProgress(myAppInfo.getPro());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView but;
        private TextView content;
        private HorizontalNumProgressBar voiceprogress;
        private LinearLayout contentback;
        private LinearLayout butback;

        public ViewHolder(View itemView) {
            super(itemView);
            but = itemView.findViewById(R.id.item_news_but);
            content = itemView.findViewById(R.id.item_news_content);
            voiceprogress = itemView.findViewById(R.id.item_news_progress);
            contentback = itemView.findViewById(R.id.item_news_contentback);
            butback = itemView.findViewById(R.id.item_news_butback);
        }
    }

    public interface Completecallback{
        void Autoreadcomplete(int pos);
    }
}
