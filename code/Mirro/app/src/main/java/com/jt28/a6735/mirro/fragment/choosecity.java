package com.jt28.a6735.mirro.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jt28.a6735.mirro.R;
import com.jt28.a6735.mirro.view.choosecity.PinnedHeaderListView;
import com.jt28.a6735.mirro.view.choosecity.cityAdapter;
import com.jt28.a6735.mirro.view.cityadpter.City;
import com.jt28.a6735.mirro.view.cityadpter.CityName;
import com.jt28.a6735.viewpager.PageFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a6735 on 2017/9/15.
 */

public class choosecity  extends PageFragment implements View.OnClickListener {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (int x = 0; x < CityName.City.length; x++) {
            List<String> group_city = new ArrayList<>();
            for (int y = 0; y < CityName.City[x].length; y++) {
                //list_city.add(new City(CityName.City[x][y],CityName.CityCode[x][y]));
                group_city.add(CityName.City[x][y]);
            }
            rightStr.add(group_city);
        }

        for (String tmp :CityName.Area) {
            leftStr.add(tmp);
        }

        pinnedListView = getActivity().findViewById(R.id.choose_city_list);
        sectionedAdapter = new cityAdapter(getContext(), leftStr, rightStr);
        pinnedListView.setAdapter(sectionedAdapter);
        sectionedAdapter.SetMenuInterface(new cityAdapter.MenuInterface() {
            @Override
            public void dochange(int gr, int ch) {
                EventBus.getDefault().post(new City("weatherclose",CityName.CityCode[gr][ch]));
            }
        });

//        messageboard_leftbtn = getActivity().findViewById(R.id.choose_city_leftbtn);
//        messageboard_leftbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // EventBus.getDefault().post(new Butcmd("choosecity"));
//                getActivity().onBackPressed();
//            }
//        });

//        l_city = getActivity().findViewById(R.id.choose_city_list);
//        l_city.setItemAnimator(new DefaultItemAnimator());
//        //设置布局管理器
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        l_city.setLayoutManager(new GridLayoutManager(getActivity(),8));
//        mcityadpter = new cityadpter(getActivity(),list_city);
//        l_city.setAdapter(mcityadpter);
//        //按钮监听
//        mcityadpter.setback(new cityadpter.choosecity() {
//            @Override
//            public void chooseback(String code) {
//                EventBus.getDefault().post(new City("weatherclose",code));
//            }
//        });
//        pinnedListView = getActivity().findViewById(R.id.choose_city_list);
//
//        sectionedAdapter = new MainSectionedAdapter(getContext(), groups, children);
//        pinnedListView.setAdapter(sectionedAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
            break;
        }
    }

    //ui线程
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
    };

    private ImageView messageboard_leftbtn;
//    private RecyclerView l_city;
//    private cityadpter mcityadpter;
//    private List<City> list_city = new ArrayList<>();

    private PinnedHeaderListView pinnedListView;
    private cityAdapter sectionedAdapter;
    private List<String> leftStr = new ArrayList<>();
    private List<List<String>> rightStr = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choosecity, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //TODO now it's visible to user
        }
    }
}
