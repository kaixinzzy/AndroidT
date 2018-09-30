package com.zzy.android.download;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zzy.event.ac.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;

public class DownloadActivity extends AppCompatActivity {
    private static final String TAG = "DownloadActivity";

    private static int THREAD_MAX = 5;// 最大下载并发数（未防止内存溢出）
    private ExecutorService pool = Executors.newFixedThreadPool(THREAD_MAX);
    private ThreadFactory f = Executors.defaultThreadFactory();

    private List<DownloadStatus> mDownloadStatuses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        // 下载数据
        mDownloadStatuses.add(new DownloadStatus("任务一", "我是任务一", "a.zip", "http://182.92.118.251:80/AutomatSys/download/version/2.08.T0.B6/apk_release.zip"));
        mDownloadStatuses.add(new DownloadStatus("任务二", "我是任务二", "b.apk", "http://apkc.mumayi.com/2015/03/06/92/927937/xingxiangyi_V3.1.3_mumayi_00169.apk"));
        // 开始下载
        for (int i = 0; i < mDownloadStatuses.size(); i++) {
            DownloadResume downloadResume = new DownloadResume(this, mDownloadStatuses.get(i));
            downloadResume.setRedownloadNum(5);
            downloadResume.DownloadStart();
        }

        try {
//            pool.execute(download);
        } catch (RejectedExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
