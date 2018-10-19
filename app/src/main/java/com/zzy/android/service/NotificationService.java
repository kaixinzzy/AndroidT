package com.zzy.android.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.zzy.android.service.ac.StartServiceActivity;
import com.zzy.event.ac.R;

/**
 * 前台服务
 * 服务本身优先级比较低，系统内存吃紧时有可能被回收掉。用前台服务，可以防止被清除。
 */
public class NotificationService extends Service {
    private static final String TAG = "NotificationService";
    private static final int NOTIFY_ID = 123;// 通知id

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        showNotification();
    }

    private void startForeground() {
        Notification notification = new Notification.Builder(this).build();
        startForeground(NOTIFY_ID, notification);
    }

    private void showNotification() {
        Log.d(TAG, "启动前台服务");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("title")
                .setContentText("content");
        // 创建通知被点击时触发的Intent
        Intent intent = new Intent(this, StartServiceActivity.class);
        // 创建任务栈Builder
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(StartServiceActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 构建通知
        final Notification notification = builder.build();
        // 显示通知
        notificationManager.notify(NOTIFY_ID, notification);
        // 将服务设置为前台服务, 当id相同，只会更新通知.
        startForeground(NOTIFY_ID, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        // 移除前台服务
        stopForeground(true);
        super.onDestroy();
    }
}
