package com.jt28.a6735.mirro.view.choosecity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jt28.a6735.mirro.R;

import java.util.List;


/**
 * 基本功能：右侧Adapter
 * 创建：王杰
 * 创建时间：16/4/14
 * 邮箱：w489657152@gmail.com
 */
public class cityAdapter extends SectionedBaseAdapter {
    private Context mContext;

    private List<String> leftStr;
    private List<List<String>> rightStr;

    public cityAdapter(Context context, List<String> leftStr, List<List<String>> rightStr) {
        this.mContext = context;
        this.leftStr = leftStr;
        this.rightStr = rightStr;
    }

    public void SetItem(List<String> leftStr, List<List<String>> rightStr) {
        this.leftStr = leftStr;
        this.rightStr = rightStr;
    }

    @Override
    public Object getItem(int section, int position) {
        return rightStr.get(section).get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return leftStr.size();
    }

    @Override
    public int getCountForSection(int section) {
        return rightStr.get(section).size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        RelativeLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (RelativeLayout) inflator.inflate(R.layout.choosecity_list_item, null);
        } else {
            layout = (RelativeLayout) convertView;
        }

        Button but_change = (Button) layout.findViewById(R.id.choosecity_list_item_name);
        but_change.setText(rightStr.get(section).get(position));
        but_change.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_MenuInterface != null) {
                    m_MenuInterface.dochange(section,position);
                }
            }
        });

        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(leftStr.get(section));
        return layout;
    }

    private MenuInterface m_MenuInterface;

    public void SetMenuInterface(MenuInterface m_MenuInterface) {
        this.m_MenuInterface = m_MenuInterface;
    }

    public interface MenuInterface {
        public void dochange(int gr, int ch);
    }
}
