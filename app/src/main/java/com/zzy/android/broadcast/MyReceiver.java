package com.zzy.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "静态广播接收器，已经收到广播");
        // 截断有序广播，将不再往下发送。
        abortBroadcast();
    }
}
