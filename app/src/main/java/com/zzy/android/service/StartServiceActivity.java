package com.zzy.android.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zzy.aidl.IUserManager;
import com.zzy.aidl.User;
import com.zzy.event.ac.R;

import java.util.List;

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
    private Intent mIntent3;
    private ServiceT mServiceT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_service);

        Log.d(TAG, "Process id is " + Process.myPid());
        Log.d(TAG, "Thread id is " + Thread.currentThread().getId());

        mIntent1 = new Intent(this, IntentServiceT.class);
        mIntent2 = new Intent(this, ServiceT.class);
        mIntent3 = new Intent(this, NotificationService.class);
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
    };

    private boolean isConnectRemote = false;
    private IUserManager mIUserManager;
    // 绑定服务后回调【远程服务】
    ServiceConnection mServiceConnectionRemote = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "绑定成功 Remote");
            isConnectRemote = true;
            mIUserManager = IUserManager.Stub.asInterface(service);
            User user = new User();
            user.setName("我是客户端");
            try {
                mIUserManager.setUser(user);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                List<User> users = mIUserManager.getUsers();
                for (User user1 : users) {
                    Log.d(TAG, user1.getName());
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "绑定失败 Remote");
            isConnectRemote = false;
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
                case R.id.start_notification_service:// 启动前台服务
                    startService(mIntent3);
                    break;
                case R.id.stop_notification_service:// 关闭前台服务
                    stopService(mIntent3);
                    break;
                case R.id.start_remote_service:// 启动远程服务
                {
                    Log.d(TAG, "启动远程服务");

//                    intent.setAction("com.zzy.android.service.remoteservice");
//                    intent.setPackage("com.zzy.event.ac");
//                    getApplication().bindService(intent, mServiceConnectionRemote, Context.BIND_AUTO_CREATE);
//                    ComponentName componentName = new ComponentName("com.zzy.event.ac", "com.zzy.android.service.RemoteService");
//                    intent.setComponent(componentName);
//                    startService(intent);

//                    Intent intent = new Intent(getApplicationContext(), RemoteService.class);
//                    getApplication().bindService(intent, mServiceConnectionRemote, Context.BIND_AUTO_CREATE);
                    // 隐式调用
                    Intent intent = new Intent();
                    intent.setAction("com.zzy.aidl");
                    intent.setPackage("com.zzy.event.ac");// 设置应用包名，不然android5.0以后隐式启动服务报异常
                    getApplication().bindService(intent, mServiceConnectionRemote, Context.BIND_AUTO_CREATE);
                    break;
                }
                case R.id.stop_remote_service:// 关闭远程服务
                {
//                    String servicePkg = "com.zzy.android.service.RemoteService";
//                    Intent intent = new Intent();
//                    ComponentName componentName = new ComponentName(this, servicePkg);
//                    intent.setComponent(componentName);
//                    stopService(intent);
                    if (isConnectRemote) {
                        unbindService(mServiceConnectionRemote);
                    }
                    break;
                }
//                case 1:// 绑定远程服务
//                {
//                    Intent intent = new Intent();
//                    ComponentName componentName = new ComponentName(this, "com.zzy.android.service.RemoteService");
//                    intent.setComponent(componentName);
//                    bindService(intent, mServiceConnectionRemote, Context.BIND_AUTO_CREATE);
//                    break;
//                }
//                case 2:// 解除远程绑定服务
//                {
//                    unbindService(mServiceConnectionRemote);
//                    break;
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
