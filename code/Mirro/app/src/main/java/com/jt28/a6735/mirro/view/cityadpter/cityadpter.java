package com.jt28.a6735.mirro.view.cityadpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jt28.a6735.mirro.R;

import java.util.List;

/**
 * Created by a6735 on 2017/9/4.
 */

public class cityadpter extends  RecyclerView.Adapter  {
    private LayoutInflater inflater;
    private List<City> list;
    private Context context;
    private choosecity back;

    public cityadpter(Context context , List<City> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setback(choosecity back) {
        this.back = back;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_cityadpter,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        final City myAppInfo = list.get(position);
        viewHolder.but.setText(myAppInfo.getCityname());
        viewHolder.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(back != null) {
                    back.chooseback(myAppInfo.getL_code());
                }
               // CityName.CityCode[position];
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private Button but;

        public ViewHolder(View itemView) {
            super(itemView);
            but = itemView.findViewById(R.id.id_item_cityadpter_but);
        }
    }

    public interface choosecity{
        void chooseback(String code);
    }
}
