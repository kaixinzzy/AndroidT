package com.zzy.android.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zzy.event.ac.R;

import java.util.Vector;

/**
 * 用系统DownloadManager下载http\https的文件
 * 权限：
 *      <uses-permission android:name="android.permission.INTERNET" />
 *      <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * 参考：
 *      https://www.jianshu.com/p/3e1467c05acf
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
                DownloadManager.Query query = new DownloadManager.Query();
//                query.setFilterById()
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        // 注册广播，用来接收下载任务的状态
        registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        // 下载数据
        mDownloadStatuses.add(new DownloadStatus("http://182.92.118.251:80/AutomatSys/download/version/2.08.T0.B6/apk_release.zip"));
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
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadStatus.getFilePath()));
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
        request.setTitle("My Data Download");
        // 通知栏显示的下载任务描述
        request.setDescription("Android Data download using DownloadManager.");
        // 是否允许MediaScanner扫描到这个文件。默认不允许。
        request.allowScanningByMediaScanner();
        // 是否允许我们下载的文件被系统的Downloads应用扫描管理，默认是允许的
        request.setVisibleInDownloadsUi(false);
        // 是否允许计量式的网络连接执行下载操作，默认允许
        request.setAllowedOverMetered(true);
        // 设置下载路径及文件名
        request.setDestinationInExternalPublicDir("/download/", "aaa".concat(".tmp"));

        if (mDownloadManager != null) {
            // 将下载任务添加到下载队列，并返回任务id，通过此id可以取消任务，重启任务等等
            long taskId = mDownloadManager.enqueue(request);
        } else {
            Log.d(TAG, "下载任务加载队列失败，原因：DownloadManager is null");
        }
    }

    /**
     * 取消下载任务，同时会删除对应的文件
     * @param taskId 任务id
     */
    private void cancelDownload(long... taskId) {
        if (mDownloadManager != null) {
            mDownloadManager.remove(taskId);
        }
    }



}
