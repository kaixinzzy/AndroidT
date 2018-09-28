package com.zzy.android.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zzy.event.ac.R;

import java.util.Vector;

/**
 * 用系统DownloadManager下载http\https的文件，适合大文件的下载
 * 权限：
 *      <uses-permission android:name="android.permission.INTERNET" />
 *      <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 *      <uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION" />
 * 参考：
 *      https://www.jianshu.com/p/3e1467c05acf
 *      https://blog.csdn.net/qq_19431333/article/details/52798105
 * 类似的可用库：https://github.com/lingochamp/okdownload
 */
public class DownloadActivity extends AppCompatActivity {
    private static final String TAG = "DownloadActivity";
    private DownloadManager mDownloadManager;
    private Vector<DownloadStatus> mDownloadStatuses = new Vector<>();

    // 广播接收下载状态
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                // 获取下载完成任务的taskId
                long taskId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                Log.d(TAG, taskId + "任务已完成");
            } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                // 当用户点击通知栏下载的任务时，我们可以执行自己的逻辑
            } else if (intent.getAction().equals(DownloadManager.ACTION_VIEW_DOWNLOADS)) {
                //
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        // 注册广播，用来接收下载任务的状态
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);// 下载完成广播
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);// 用户点击下载通知栏
        intentFilter.addAction(DownloadManager.ACTION_VIEW_DOWNLOADS);//
        registerReceiver(mReceiver, new IntentFilter());
        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        // 下载数据
        mDownloadStatuses.add(new DownloadStatus("任务一", "一开始下载了", "http://182.92.118.251:80/AutomatSys/download/version/2.08.T0.B6/apk_release.zip"));
//        mDownloadStatuses.add(new DownloadStatus("任务二", "二开始下载了", "http://apkc.mumayi.com/2015/03/06/92/927937/xingxiangyi_V3.1.3_mumayi_00169.apk"));
        // 开始下载
        for (int i = 0; i < mDownloadStatuses.size(); i++) {
            startDownload(mDownloadStatuses.get(i));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    /**
     * 开始下载
     * @param downloadStatus
     */
    private void startDownload(DownloadStatus downloadStatus) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadStatus.getDownloadPath()));
        // 设置当网络连接类型为wifi or mobile时，允许下载。如果当前网络不符合条件，则下载会等待.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager
                .Request.NETWORK_MOBILE);
        // 移动网络情况下是否允许漫游，默认为true
        request.setAllowedOverRoaming(true);
        // 是否通知栏显示
        // Request.VISIBILITY_VISIBLE：在下载进行的过程中，通知栏中会一直显示该下载的Notification，当下载完成时，该Notification会被移除，这是默认的参数值。
        // Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED：在下载过程中通知栏会一直显示该下载的Notification，
        //      在下载完成后该Notification会继续显示，直到用户点击该Notification或者消除该Notification。
        // Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION：只有在下载完成后该Notification才会被显示。
        // Request.VISIBILITY_HIDDEN：不显示该下载请求的Notification。如果要使用这个参数，需要在应用的清单文件中加上DOWNLOAD_WITHOUT_NOTIFICATION权限。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        // 通知栏显示的下载任务标题
        request.setTitle(downloadStatus.getNotificationTitle());
        // 通知栏显示的下载任务描述
        request.setDescription(downloadStatus.getNotificationDescription());
        // 是否允许MediaScanner扫描到这个文件。默认不允许。
        request.allowScanningByMediaScanner();
        // 是否允许我们下载的文件被系统的Downloads应用扫描管理，默认是允许的
        request.setVisibleInDownloadsUi(false);
        // 是否允许计量式的网络连接执行下载操作，默认允许
        request.setAllowedOverMetered(true);
        // 设置下载路径及文件名
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "aaa".concat(".tmp"));

        if (mDownloadManager != null) {
            // 把任务加入下载队列并返回taskId，以便后面用于查询下载信息。若网络不满足条件、Sdcard挂载中、超过最大并发数等异常会等待下载，正常则直接下载。
            // 通过此taskId可以取消任务，重启任务等等
            final long taskId = mDownloadManager.enqueue(request);
            downloadStatus.setTaskId(taskId);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        queryStatus(taskId);
                    }
                }
            }).start();

        } else {
            Log.d(TAG, "下载任务加载队列失败，原因：DownloadManager is null");
        }
    }

    /**
     * 查询下载进度
     * @param downloadStatus
     */
    private void queryStatus(DownloadStatus downloadStatus) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadStatus.getTaskId());
        Cursor cursor = null;
        try {
            cursor = mDownloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                // taskId
                String taskId = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                // 下载文件本地完整路径，包括文件名
                String name = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                downloadStatus.setLocalFilePath(name);
                // 下载文件网络地址
                String uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
                // 截止目前已经下载的文件总大小
                int currentSize = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                // 下载文件的总大小
                int count = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                downloadStatus.setPercent(percent(currentSize, count));
                // 下载状态
                int status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                switch (status) {
                    case DownloadManager.STATUS_PAUSED:// 暂停
                        downloadStatus.setStatus(DownloadStatus.Status.PAUSED);
                        break;
                    case DownloadManager.STATUS_PENDING:// 下载中
                        downloadStatus.setStatus(DownloadStatus.Status.PENDING);
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:// 下载成功
                        downloadStatus.setStatus(DownloadStatus.Status.SUCCESSFUL);
                        break;
                    case DownloadManager.STATUS_FAILED:// 下载失败
                        downloadStatus.setStatus(DownloadStatus.Status.FAILED);
                        break;
                    default:// 未知状态
                        downloadStatus.setStatus(DownloadStatus.Status.UNKNOWN);
                        break;
                }
                Log.d(TAG, downloadStatus.getTaskId() + " " + downloadStatus.getPercent() + " name:" + name + " uri:" + uri);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * 取消下载任务，同时会删除对应的文件和记录
     * @param taskId 任务id
     */
    private void cancelDownload(long... taskId) {
        if (mDownloadManager != null) {
            mDownloadManager.remove(taskId);
        }
    }

    /**
     * 计算下载文件的百分比
     * @param currentSize 当前下载的字节数
     * @param count 文件的总字节数
     * @return
     */
    public String percent(int currentSize, int count) {
        if (currentSize <= 0 || count < 0) {
            return "";
        }
        int part = count/100;
        return currentSize/part + "%";
    }



}
