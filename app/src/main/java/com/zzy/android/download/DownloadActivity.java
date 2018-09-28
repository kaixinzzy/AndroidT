package com.zzy.android.download;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zzy.event.ac.R;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {
    private static final String TAG = "DownloadActivity";

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
