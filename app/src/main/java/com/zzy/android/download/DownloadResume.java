package com.zzy.android.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

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
public class DownloadResume {
    private static final String TAG = "DownloadResume";
    private Context mContext;
    private DownloadManager mDownloadManager;
    private DownloadManager.Request mRequest;
    private DownloadStatus mDownloadStatus;
    private int mRedownloadNum = 3;// 下载重试次数

    // 广播接收下载状态
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                // 获取下载完成任务的taskId
                long taskId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (null != mDownloadStatus && mDownloadStatus.getTaskId() == taskId) {
                    // MD5存在，则对比MD5看文件是否完整
                    if (!TextUtils.isEmpty(mDownloadStatus.getMd5())) {
                        if (MD5Compare(mDownloadStatus.getLocalFilePath(), mDownloadStatus.getMd5())) {
                            // 下载文件完整，下载完成
                            onDestroy();
                            Log.d(TAG, taskId + "任务已完成 MD5已校验");
                        } else {
                            // 下载文件损坏，重新下载
                            if (mRedownloadNum > 0) {
                                mRedownloadNum--;
                                Log.d(TAG, " 重试下载 " + mRedownloadNum);
                                DownloadStart();
                            }
                        }
                    } else {
                        // MD5不存在，下载完成
                        onDestroy();
                        Log.d(TAG, taskId + "任务已完成 MD5未校验");
                    }
                }

            } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                // 当用户点击通知栏下载的任务时，我们可以执行自己的逻辑
            } else if (intent.getAction().equals(DownloadManager.ACTION_VIEW_DOWNLOADS)) {
                //
            }
        }
    };

    public DownloadResume(Context context, DownloadStatus downloadStatus) {
        mContext = context;
        mDownloadStatus = downloadStatus;
        // 注册广播，用来接收下载任务的状态
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);// 下载完成广播
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);// 用户点击下载通知栏
        intentFilter.addAction(DownloadManager.ACTION_VIEW_DOWNLOADS);//
        // 可以不用广播监听下载状态，直接开启线程从数据库读取就好
//        mContext.registerReceiver(mReceiver, intentFilter);
        mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadInit();
    }

    /**
     * 下载完成或销毁此次下载是，记得销毁注册的广播
     */
    private void onDestroy() {
//        mContext.unregisterReceiver(mReceiver);
    }

    /**
     * 下载初始化
     */
    private void downloadInit() {
        mRequest = new DownloadManager.Request(Uri.parse(mDownloadStatus.getDownloadPath()));
        // 设置当网络连接类型为wifi or mobile时，允许下载。如果当前网络不符合条件，则下载会等待.
        mRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager
                .Request.NETWORK_MOBILE);
        // 移动网络情况下是否允许漫游，默认为true
        mRequest.setAllowedOverRoaming(true);
        // 是否通知栏显示
        // Request.VISIBILITY_VISIBLE：在下载进行的过程中，通知栏中会一直显示该下载的Notification，当下载完成时，该Notification会被移除，这是默认的参数值。
        // Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED：在下载过程中通知栏会一直显示该下载的Notification，
        //      在下载完成后该Notification会继续显示，直到用户点击该Notification或者消除该Notification。
        // Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION：只有在下载完成后该Notification才会被显示。
        // Request.VISIBILITY_HIDDEN：不显示该下载请求的Notification。如果要使用这个参数，需要在应用的清单文件中加上DOWNLOAD_WITHOUT_NOTIFICATION权限。
        mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        // 通知栏显示的下载任务标题
        mRequest.setTitle(mDownloadStatus.getNotificationTitle());
        // 通知栏显示的下载任务描述
        mRequest.setDescription(mDownloadStatus.getNotificationDescription());
        // 是否允许MediaScanner扫描到这个文件。默认不允许。
        mRequest.allowScanningByMediaScanner();
        // 是否允许我们下载的文件被系统的Downloads应用扫描管理，默认是允许的
        mRequest.setVisibleInDownloadsUi(false);
        // 是否允许计量式的网络连接执行下载操作，默认允许
        mRequest.setAllowedOverMetered(true);
        // 设置下载路径及文件名
        mRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, mDownloadStatus.getLocalFileName());
    }

    /**
     * 开始下载 or 重新开始下载
     */
    public void DownloadStart() {
        if (mDownloadManager != null) {
            // 把任务加入下载队列并返回taskId，以便后面用于查询下载信息。若网络不满足条件、Sdcard挂载中、超过最大并发数等异常会等待下载，正常则直接下载。
            // 通过此taskId可以取消任务，重启任务等等
            final long taskId = mDownloadManager.enqueue(mRequest);
            mDownloadStatus.setTaskId(taskId);
            // 轮询获取下载文件的状态，直到下载完成
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean downloading = true;
                    while (downloading) {
                        downloading = queryStatus();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } else {
            Log.d(TAG, "下载任务加载队列失败，原因：DownloadManager is null");
        }
    }

    /**
     * 查询下载进度
     */
    private boolean queryStatus() {
        boolean downloading = true;
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(mDownloadStatus.getTaskId());
        Cursor cursor = null;
        try {
            cursor = mDownloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                // taskId
                String taskId = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                // 下载文件本地完整路径，包括文件名
                String name = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                mDownloadStatus.setLocalFilePath(name);
                // 下载文件网络地址
                String uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
                // 截止目前已经下载的文件总大小
                int currentSize = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                // 下载文件的总大小
                int count = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                mDownloadStatus.setPercent(percent(currentSize, count));
                // 下载状态
                int status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                // 下载失败原因
                int reason = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_REASON));
                switch (status) {
                    case DownloadManager.STATUS_PAUSED:// 暂停
                        mDownloadStatus.setStatus(DownloadStatus.Status.PAUSED);
                        break;
                    case DownloadManager.STATUS_PENDING:// 下载中
                        mDownloadStatus.setStatus(DownloadStatus.Status.PENDING);
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:// 下载成功
                        mDownloadStatus.setStatus(DownloadStatus.Status.SUCCESSFUL);
                        downloading = false;
                        // MD5存在，则对比MD5看文件是否完整
                        if (!TextUtils.isEmpty(mDownloadStatus.getMd5())) {
                            if (MD5Compare(mDownloadStatus.getLocalFilePath(), mDownloadStatus.getMd5())) {
                                // 下载文件完整，下载完成
                                onDestroy();
                                Log.d(TAG, taskId + "任务已完成 MD5已校验");
                            } else {
                                // 下载文件损坏，重新下载
                                if (mRedownloadNum > 0) {
                                    mRedownloadNum--;
                                    Log.d(TAG, " 重试下载 " + mRedownloadNum);
                                    DownloadStart();
                                }
                            }
                        } else {
                            // MD5不存在，下载完成
                            onDestroy();
                            Log.d(TAG, taskId + "任务已完成 MD5未校验");
                        }
                        break;
                    case DownloadManager.STATUS_FAILED:// 下载失败
                        mDownloadStatus.setStatus(DownloadStatus.Status.FAILED);
                        downloading= false;
                        if (mRedownloadNum > 0) {
                            mRedownloadNum--;
                            Log.d(TAG, "重试下载 " + mRedownloadNum);
                            DownloadStart();
                        }
                        switch (reason) {
                            case DownloadManager.ERROR_CANNOT_RESUME:
                                Log.d(TAG, "下载失败原因 ERROR_CANNOT_RESUME");
                                break;
                            case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                                Log.d(TAG, "下载失败原因 ERROR_DEVICE_NOT_FOUND");
                                break;
                            case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                                Log.d(TAG, "下载失败原因 ERROR_FILE_ALREADY_EXISTS");
                                break;
                            case DownloadManager.ERROR_FILE_ERROR:
                                Log.d(TAG, "下载失败原因 ERROR_FILE_ERROR");
                                break;
                            case DownloadManager.ERROR_HTTP_DATA_ERROR:
                                Log.d(TAG, "下载失败原因 ERROR_HTTP_DATA_ERROR");
                                break;
                            case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                                Log.d(TAG, "下载失败原因 ERROR_INSUFFICIENT_SPACE");
                                break;
                            case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                                Log.d(TAG, "下载失败原因 ERROR_TOO_MANY_REDIRECTS");
                                break;
                            case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                                Log.d(TAG, "下载失败原因 ERROR_UNHANDLED_HTTP_CODE");
                                break;
                            case DownloadManager.ERROR_UNKNOWN:
                                Log.d(TAG, "下载失败原因 ERROR_UNKNOWN");
                                break;
                            case 404:
                                Log.d(TAG, "下载失败原因 ERROR_HTTP_404,url invalid");
                                break;
                            default:
                                Log.d(TAG, "下载失败原因 ERROR_UNKNOWN");
                                break;
                        }
                        break;
                    default:// 未知状态
                        mDownloadStatus.setStatus(DownloadStatus.Status.UNKNOWN);
                        break;
                }
                Log.d(TAG, mDownloadStatus.getTaskId()
                        + " " + mDownloadStatus.getPercent()
                        + " " + mDownloadStatus.getStatus()
                        + " name:" + name
                        + " uri:" + uri);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return downloading;
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
    private String percent(int currentSize, int count) {
        if (currentSize <= 0 || count < 0) {
            return "";
        }
        int part = count/100;
        return currentSize/part + "%";
    }

    /**
     * 通过对比MD5，下载文件的完整性
     * @param filePath 下载文件的路径
     * @param md5 服务器给的md5值
     */
    private boolean MD5Compare(String filePath, String md5) {
        String currentFileMd5 = getFileMD5(filePath);
        if (null != currentFileMd5 && currentFileMd5.equalsIgnoreCase(md5)) {
            return true;
        }
        return false;
    }

    /**
     * 读取本地文件的MD5
     * @param filePath 本地文件路径
     * @return
     */
    private String getFileMD5(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 下载重试次数设定
     * @param redownloadNum 重试次数
     */
    public void setRedownloadNum(int redownloadNum) {
        mRedownloadNum = redownloadNum;
    }
}
