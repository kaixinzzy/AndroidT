package com.zzy.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.zzy.event.ac.R;

public class LocalBroadcastActivity extends AppCompatActivity {
    private static final String TAG = "LocalBroadcastActivity";

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_broadcast);

        localBroadcastManager = LocalBroadcastManager.getInstance(this); // 获取实例
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zzy.broadcast2");
        // 注册本地广播
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);

        // 发送广播 -- 标准广播 只有本应用内才能接受到的广播
        Intent intent = new Intent("com.zzy.broadcast2");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //
        if (broadcastReceiver != null && localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(broadcastReceiver);
        }
    }
}
