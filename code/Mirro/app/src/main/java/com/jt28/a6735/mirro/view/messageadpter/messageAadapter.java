package com.jt28.a6735.mirro.view.messageadpter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.tool.GetResources;
import com.jt28.a6735.mirro.tool.Voice.VoiceManager;

import java.util.List;

/**
 * Created by a6735 on 2017/8/30.
 */

public class messageAadapter extends  RecyclerView.Adapter {
    private LayoutInflater inflater;
    private List<message> list;
    private Context context;
    private VoiceManager voiceManager;
    private int lastPosition = -1;

    public messageAadapter(Context context , List<message> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        voiceManager =  VoiceManager.getInstance(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_message,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        switch (list.get(position).getType()) {
            case 0:{//语音
                Resources resources = context.getResources();
                Drawable imageDrawable = resources.getDrawable(R.mipmap.home_icon_voice); //图片在drawable目录下
                viewHolder.type.setBackgroundDrawable(imageDrawable);
                viewHolder.back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                                    list.get(position).setRead(true);
                                    viewHolder.noread.setBackgroundResource(0);
                                }
                            });
                            voiceManager.startPlay(list.get(position).getPath());
                        }
                        lastPosition = position;
                    }
                });
            }
                break;
            case 1:{//图片
                Resources resources = context.getResources();
                Drawable imageDrawable = resources.getDrawable(R.mipmap.home_icon_image); //图片在drawable目录下
                viewHolder.type.setBackgroundDrawable(imageDrawable);
                viewHolder.back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GetResources.startRecordDialog(context,list.get(position).getPath());
                        list.get(position).setRead(true);
                        viewHolder.noread.setBackgroundResource(0);
                    }
                });
            }
                break;
            case 2:{//视屏
                Resources resources = context.getResources();
                Drawable imageDrawable = resources.getDrawable(R.mipmap.home_icon_video); //图片在drawable目录下
                viewHolder.type.setBackgroundDrawable(imageDrawable);
            }
                break;
        }
        viewHolder.name.setText(list.get(position).getContent());
        if(list.get(position).isRead()) {
            viewHolder.noread.setBackgroundResource(0);
        } else {
            Resources resources = context.getResources();
            Drawable imageDrawable = resources.getDrawable(R.drawable.item_noread); //图片在drawable目录下
            viewHolder.noread.setBackgroundDrawable(imageDrawable);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private ImageView type;
        private View noread;
        private ImageView back;

        public ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.item_message_type);
            name = itemView.findViewById(R.id.item_message_name);
            noread = itemView.findViewById(R.id.item_message_noread);
            back = itemView.findViewById(R.id.item_message_background);
        }
    }
}
