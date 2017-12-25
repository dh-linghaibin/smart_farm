package com.jt28.a6735.mirro.view.bigmessageadpter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.tool.GetResources;
import com.jt28.a6735.mirro.tool.Voice.VoiceManager;
import com.jt28.a6735.mirro.view.messageadpter.message;

import java.util.List;

/**
 * Created by a6735 on 2017/9/1.
 */

public class bigmessageAadapter extends  RecyclerView.Adapter {
    private LayoutInflater inflater;
    private List<message> list;
    private Context context;
    private VoiceManager voiceManager;
    private int lastPosition = -1;

    public bigmessageAadapter(Context context , List<message> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        voiceManager =  VoiceManager.getInstance(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_bigmessage,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final message p_message= list.get(position);

        switch (p_message.getType()) {
            case 0:{//语音
                Resources resources = context.getResources();
                Drawable imageDrawable = resources.getDrawable(R.mipmap.icon_voice); //图片在drawable目录下
                viewHolder.type.setBackgroundDrawable(imageDrawable);

                //背景控制
                viewHolder.voice_time.setText((int) p_message.getLongtime() + "''");

                viewHolder.newtimegroup.setVisibility(View.VISIBLE);
                viewHolder.back.setVisibility(View.GONE);

                viewHolder.newtimegroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("ling","语音按下");
                        if (voiceManager.isPlaying()&&lastPosition == position) {
                            voiceManager.stopPlay();
                        }else{
                            voiceManager.stopPlay();
                            voiceManager.setVoicePlayListener(new VoiceManager.VoicePlayCallBack() {
                                @Override
                                public void voiceTotalLength(long time, String strTime) {

                                }

                                @Override
                                public void playDoing(long time, String strTime) {

                                }

                                @Override
                                public void playPause() {

                                }

                                @Override
                                public void playStart() {

                                }

                                @Override
                                public void playFinish() {

                                }
                            });
                            voiceManager.startPlay(p_message.getPath());
                        }
                        lastPosition = position;
                    }
                });
            }
            break;
            case 1:{//图片
                Resources resources = context.getResources();
                Drawable imageDrawable = resources.getDrawable(R.mipmap.icon_image); //图片在drawable目录下
                viewHolder.type.setBackgroundDrawable(imageDrawable);

                //背景控制
                viewHolder.newtimegroup.setVisibility(View.GONE);
                viewHolder.back.setVisibility(View.VISIBLE);
                viewHolder.back.setImageBitmap(GetResources.getDiskBitmap(p_message.getPath()));
                viewHolder.back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("ling","视屏按下");
                        GetResources.startRecordDialog(context,p_message.getPath());
                    }
                });
            }
            break;
            case 2:{//视屏
                Resources resources = context.getResources();
                Drawable imageDrawable = resources.getDrawable(R.mipmap.icon_video); //图片在drawable目录下
                viewHolder.type.setBackgroundDrawable(imageDrawable);
            }
            break;
        }
        viewHolder.time.setText(p_message.getNewtime());
       // viewHolder.equals()
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView type;
        private ImageView back;
        private TextView voice_time;
        private TextView time;
        private RelativeLayout newtimegroup;

        public ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.item_bigmessage_type);
            back = itemView.findViewById(R.id.item_bigmessage_back);
            voice_time = itemView.findViewById(R.id.item_bigmessage_voicetime);
            time = itemView.findViewById(R.id.item_bigmessage_nowtime);
            newtimegroup = itemView.findViewById(R.id.item_bigmessage_voicetimegroup);
        }
    }
}
