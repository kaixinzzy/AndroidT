package com.zzy.android.service.ac;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zzy.android.service.MessengerService;
import com.zzy.event.ac.R;

public class MessengerServiceActivity extends AppCompatActivity {
    private static final String TAG = MessengerServiceActivity.class.getSimpleName();

    private boolean isConnected;
    private Messenger mMessenger;

    public final MyHandler mHandler = new MyHandler();
    public static final int msg_what = 1;

    public static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msg_what:
                    Log.d(TAG, "收到服务端信息-------"+msg.getData().get("key"));
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_service);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isConnected = true;
            mMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
            mMessenger = null;
        }
    };

    public void startService(View view) {
    }

    public void stopService(View view) {
    }

    public void bindService(View view) {
        Intent intent = new Intent();
        intent.setAction("com.zzy.messenger");
        intent.setPackage("com.zzy.event.ac");// // 设置应用包名，不然android5.0以后隐式启动服务报异常
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        if (isConnected) {
            unbindService(mServiceConnection);
        }
    }

    // 向服务发送消息
    public void sendMessagetoService(View view) {
        if (isConnected && mMessenger != null) {
            Message msg = Message.obtain(null, MessengerService.msg_what);
            Bundle bundle = new Bundle();
            bundle.putString("key", "我是Client");
            msg.setData(bundle);
            // Service回复消息用
            msg.replyTo = new Messenger(mHandler);
            try {
                mMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
