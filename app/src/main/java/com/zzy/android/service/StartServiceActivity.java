package com.zzy.android.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zzy.event.ac.R;

/**
 * Service
 * 参考：
 *      https://blog.csdn.net/jiangwei0910410003/article/details/17008687
 *      Service后台服务、前台服务、IntentService、跨进程服务、无障碍服务、系统服务 https://www.ctolib.com/topics-96910.html
 *      Service 和 IntentService 的区别 http://blog.qiji.tech/archives/2693
 *      https://blog.csdn.net/column/details/17926.html
 *
 */
public class StartServiceActivity extends AppCompatActivity implements View.OnClickListener {
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
    }

    // 绑定服务后回调
    ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override // 绑定成功
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "绑定成功");
            ServiceT.MyBinder myBinder = (ServiceT.MyBinder)service;
            mServiceT = myBinder.getService();
        }

        @Override // 绑定失败
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "绑定失败");
        }

        @Override
        public void onBindingDied(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.start_intent_service:// 启动IntentService服务
                    startService(mIntent1);
                    break;
                case R.id.stop_intent_service:
                    stopService(mIntent1);
                    break;
                case R.id.start_service:// 启动服务
//                    Context.startForegroundService(mIntent2);// 启动前台服务
                    startService(mIntent2);
                    break;
                case R.id.stop_service:// 停止服务
                    stopService(mIntent2);
                    break;
                case R.id.bind_service:// 绑定服务
                    bindService(mIntent2, mServiceConnection, Service.BIND_AUTO_CREATE);
                    break;
                case R.id.unbind_service:// 解除绑定服务
                    if (mServiceT != null) {
                        unbindService(mServiceConnection);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
