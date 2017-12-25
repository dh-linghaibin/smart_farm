package com.jt28.a6735.smart_farm;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.jt28.a6735.movelayout.DragerViewLayout;
import com.jt28.a6735.smart_farm.msg.FragmentCountMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity {
    private DragerViewLayout drager_layout;
    private LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        drager_layout = (DragerViewLayout) findViewById(R.id.drager_layout);
        drager_layout.isDrager(false);
        drager_layout.setFilePathAndName(Environment.getExternalStorageDirectory().getPath() + "/smart-farm", "weds");

        layout = (LinearLayout) findViewById(R.id.activity_main_layout);
        layout.setLayoutAnimation(getAnimationController());//这是第一种方式
    }

    protected LayoutAnimationController getAnimationController() {
        LayoutAnimationController controller;
        // AnimationSet set = new AnimationSet(true);
        Animation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);// 从0.5倍放大到1倍
        anim.setDuration(1500);
        controller = new LayoutAnimationController(anim, 0.1f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return controller;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    boolean flag = true;
    @Subscribe(sticky = true)
    public void onEvent(FragmentCountMsg msg) {
        System.out.println("lhb" + msg.message);
        if(flag) {
            flag = false;
            layout.setVisibility(View.GONE);
            layout.setLayoutAnimation(getAnimationController());//这是第一种方式
        } else {
            flag = true;
            layout.setVisibility(View.VISIBLE);
        }
    }
}
