package com.zzy.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 *
 */
public class ServiceT extends Service {
    private static final String TAG = "ServiceT";

    @Override // 启动服务，多次启动，只会执行一次
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override // 绑定服务
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return new MyBinder();
    }

    @Override // 接触绑定服务
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        // 将此服务返回给Activity
        public ServiceT getService() {
            return ServiceT.this;
        }
    }

    /**
     * 执行后台操作，如果执行耗时操作，必须开启一个单独的线程，不然会ANR异常
     * 多次启动，会执行多次。【此处会产生线程安全问题】
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand 开始睡眠");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onStartCommand 睡眠结束");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // 销毁服务
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
