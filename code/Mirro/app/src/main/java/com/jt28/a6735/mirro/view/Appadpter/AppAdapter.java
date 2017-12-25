package com.jt28.a6735.mirro.view.Appadpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.tool.App.MyAppInfo;

import java.util.List;

/**
 * Created by a6735 on 2017/9/4.
 */

public class AppAdapter extends  RecyclerView.Adapter  {
    private LayoutInflater inflater;
    private List<MyAppInfo> list;
    private Context context;
    private Chickaction action;

    public interface Chickaction {
        void onData(String url);
    }
    //设置监听
    public void setAction(Chickaction action) {
        this.action = action;
    }

    public AppAdapter(Context context , List<MyAppInfo> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_appadapter,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final MyAppInfo myAppInfo = list.get(position);
        viewHolder.icon.setImageDrawable(myAppInfo.getImage());
//        viewHolder.name.setText(myAppInfo.getAppName());
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(action != null) {
                    action.onData(myAppInfo.getPackname());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_app_icon);
//            name = itemView.findViewById(R.id.item_app_name);
        }
    }
}
