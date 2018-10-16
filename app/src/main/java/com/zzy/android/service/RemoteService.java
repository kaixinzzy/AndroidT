package com.zzy.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

import com.zzy.aidl.IUserManager;
import com.zzy.aidl.User;

import java.util.ArrayList;
import java.util.List;

/**
 * 远程服务
 * 在单独的进程启动服务。
 * 不占用应用进程的内存。但交互起来麻烦点，
 * 涉及到进程间通信IPC，Android提供了AIDL（Android interface definition language 接口描述语言）
 */
public class RemoteService extends Service {
    private static final String TAG = "RemoteService";

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        Log.d(TAG, "Process id is " + Process.myPid());
        Log.d(TAG, "Thread id is " + Thread.currentThread().getId());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand 开始睡眠");
        int count = 1;
        while (count < 5000) {
            Log.d(TAG, "当前时间：" + count++ + "s");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "onStartCommand 睡眠结束");
        return super.onStartCommand(intent, flags, startId);
    }

    private List<User> mUsers = new ArrayList<>();

    private final IUserManager.Stub mUserManager = new IUserManager.Stub() {

        @Override
        public List<User> getUsers() throws RemoteException {
            return mUsers;
        }

        @Override
        public void setUser(User user) throws RemoteException {
            synchronized (this) {
                if (user == null) return;
                Log.d(TAG, user.getName());
                // 修改属性，看客户端数据变化
                user.setName("我是服务端");
                Log.d(TAG, user.getName());
                if (!mUsers.contains(user)) {
                    mUsers.add(user);
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mUserManager;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
