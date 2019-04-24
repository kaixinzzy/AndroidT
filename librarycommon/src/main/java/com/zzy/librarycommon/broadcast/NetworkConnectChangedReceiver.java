package com.zzy.librarycommon.broadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zzy.librarycommon.module.NetworkChangeEvent;
import com.zzy.librarycommon.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 网络连接检测，需要静态注册
 * https://mp.weixin.qq.com/s/W9U7uzXRYcqCmgbX3gbbKg
 */
public class NetworkConnectChangedReceiver extends BroadcastReceiver {
    private static final String TAG = "NetworkConnectChanged";

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        // 判断当前网络连接状态是否可用
        boolean isConnected = NetUtils.isConnected(context);
        Log.d(TAG, "onReceive:当前网络 " + isConnected);
        // EventBus通知所有注册的对象
        EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
    }

}
