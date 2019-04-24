package com.zzy.librarycommon.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zzy.librarycommon.module.NetworkChangeEvent;
import com.zzy.librarycommon.utils.ActivityManagerT;
import com.zzy.librarycommon.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private boolean mCheckNetwork = true;// 默认检查网络状态

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 管理activity
        ActivityManagerT.getInstance().addActivity(this);
        // 初始化ButterKnife
        ButterKnife.bind(this);
        // 注册EventBus
        EventBus.getDefault().register(this);
        // 在无网络情况下打开APP时，系统不会发送网络状况变更的Intent，需要自己手动检查
        onNetworkChangeEvent(new NetworkChangeEvent(NetUtils.isConnected(this)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销管理activity
        ActivityManagerT.getInstance().removeActivity(this);
        // 注销EventBus
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {
        // 网络状态发生变化，请指示
    }

    public boolean isCheckNetwork() {
        return mCheckNetwork;
    }

    /** 可以通过此方法，控制此页面是否监听网络变化 **/
    public void setCheckNetwork(boolean checkNetwork) {
        mCheckNetwork = checkNetwork;
    }

}
