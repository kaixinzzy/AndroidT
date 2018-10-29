package com.zzy.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.zzy.android.service.ac.MessengerServiceActivity;

public class MessengerService extends Service {
    private static final String TAG = MessengerService.class.getSimpleName();

    public final MyHandler mHandler = new MyHandler();
    public static final int msg_what = 0;

    public static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msg_what:
                    // 接受Client消息
                    Log.d(TAG,"收到客户端信息-------"+msg.getData().get("key"));
                    // 回复消息给Client
                    Messenger messenger = msg.replyTo;
                    Message message = Message.obtain(null, MessengerServiceActivity.msg_what);
                    Bundle bundle = new Bundle();
                    bundle.putString("key", "我是服务端，已收到你的消息");
                    message.setData(bundle);
                    try {
                        messenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Messenger(mHandler).getBinder();
    }
}
