package com.zzy.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zzy.event.ac.R;

public class BroadcastActivity extends AppCompatActivity {
    private static final String TAG = "BroadcastActivity";

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 广播接收消息
            // 切勿进行耗时操作
            Log.d(TAG, "我收到广播了");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        // 广播过滤器，说明我们要接收的广播，可以是系统广播和自定义广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE"); // 系统广播，网络连接变化
        intentFilter.addAction("com.zzy.broadcast1");                   // 自定义广播
        // 动态注册广播接收器
        registerReceiver(broadcastReceiver, intentFilter);

        // 发送广播 -- 标准广播
        Intent intent = new Intent("com.zzy.broadcast1");
        sendBroadcast(intent);

        //　发送广播 -- 有序广播
        sendOrderedBroadcast(intent, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 动态注销广播
        unregisterReceiver(broadcastReceiver);
    }
}
