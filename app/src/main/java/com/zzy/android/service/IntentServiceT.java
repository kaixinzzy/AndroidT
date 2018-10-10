package com.zzy.android.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * 在 IntentService 内有一个工作线程来处理耗时操作
 * 当任务执行完后，IntentService 会自动停止，而不需要我们去手动控制。
 * 可以启动 IntentService 多次，而每一个耗时操作会以工作队列的方式在IntentService 的 onHandleIntent 回调方法中执行，
 *      并且，每次只会执行一个工作线程，执行完第一个再执行第二个，以此类推。
 */
public class IntentServiceT extends IntentService {
    private static final String TAG = "IntentServiceT";

    // 这个写法不要变
    public IntentServiceT() {
        super("IntentServiceT");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override // 此处可以处理耗时操作。多任务的时候，依次执行
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent 开始睡眠");
        try {
            Thread.sleep(3000);// 此处无论休眠多少秒都不会超时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onHandleIntent 睡眠结束");
    }

    @Override // 队列中的任务全部执行完成后，会自动调用此方法
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
