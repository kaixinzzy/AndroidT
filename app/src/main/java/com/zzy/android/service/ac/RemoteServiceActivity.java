package com.zzy.android.service.ac;

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
 * 启动远程服务，并通过AIDL交互
 * 参考：
 *      https://blog.csdn.net/flowingflying/article/details/8030609
 */
public class RemoteServiceActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_service);

        Log.d(TAG, "Process id is " + Process.myPid());
        Log.d(TAG, "Thread id is " + Thread.currentThread().getId());
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

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


    public void startService(View view) {
        // Intent显示启动
        Intent intent = new Intent();
        intent.setClassName("com.zzy.event.ac", "com.zzy.android.service.RemoteService");
        startService(intent);
        // Intent隐式启动
//        intent.setAction("com.zzy.aidl");
//        intent.setPackage("com.zzy.event.ac");// 设置应用包名，不然android5.0以后隐式启动服务报异常
//        startService(intent);
    }

    public void stopService(View view) {
        // Intent显示关闭
        Intent intent = new Intent();
        intent.setClassName("com.zzy.event.ac", "com.zzy.android.service.RemoteService");
        stopService(intent);
    }

    public void bindService(View view) {
        Intent intent = new Intent();
        intent.setAction("com.zzy.aidl");
        intent.setPackage("com.zzy.event.ac");// 设置应用包名，不然android5.0以后隐式启动服务报异常
        getApplication().bindService(intent, mServiceConnectionRemote, Context.BIND_AUTO_CREATE);
    }

    public void unbindService(View view) {
        if (isConnectRemote) {
            unbindService(mServiceConnectionRemote);
        }
    }
}
