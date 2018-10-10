package com.zzy.android.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.zzy.event.ac.R;

/**
 * Service
 * 超时：
 *      按键或触摸事件在特定时间内无响应，超时时间5秒
 *      BroadcastReceiver在特定时间内无法处理完成，超时时间10秒
 *      前台service无响应的超时时间20秒【前台service值xml中android:exported="false"】
 *      后台service无响应的超时时间200秒【前台service值xml中android:exported="true"】
 * 参考：
 *      Service后台服务、前台服务、IntentService、跨进程服务、无障碍服务、系统服务 https://www.ctolib.com/topics-96910.html
 *      Service 和 IntentService 的区别 http://blog.qiji.tech/archives/2693
 *      https://blog.csdn.net/column/details/17926.html
 *
 */
public class StartServiceActivity extends AppCompatActivity {
    private static final String TAG = "StartServiceActivity";

    private Intent mIntent1;
    private Intent mIntent2;
    private ServiceT mServiceT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_service);

        mIntent1 = new Intent(this, IntentServiceT.class);
        mIntent2 = new Intent(this, ServiceT.class);

        // 启动IntentService服务
        startService(mIntent1);
        // 启动服务
//        startService(mIntent2);
        // 绑定服务
//        bindService(mIntent2, mServiceConnection, Service.BIND_AUTO_CREATE);
    }

    // 绑定服务后回调
    ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override // 绑定成功
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceT.MyBinder myBinder = (ServiceT.MyBinder)service;
            mServiceT = myBinder.getService();
        }

        @Override // 绑定失败
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onBindingDied(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 停止服务
//        stopService(mIntent2);
        // 接触绑定服务
        if (mServiceT != null) {
            unbindService(mServiceConnection);
        }
    }

}
