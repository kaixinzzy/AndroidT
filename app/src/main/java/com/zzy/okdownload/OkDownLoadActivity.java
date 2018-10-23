package com.zzy.okdownload;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.zzy.event.ac.R;

import java.io.File;
import java.util.List;
import java.util.Map;

public class OkDownLoadActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private final String url0 = "http://apkc.mumayi.com/2015/03/06/92/927937/xingxiangyi_V3.1.3_mumayi_00169.apk";
    private final String url1 = "http://182.92.118.251:80/AutomatSys/download/version/2.08.T0.B6/apk_release.zip";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_down_load);


    }

    // 开启单个下载任务
    public void StartTask() {
        DownloadTask task = new DownloadTask.Builder(url0, new File("/sdcard/download/1.apk"))
                .setFilename("1.apk")
                .setMinIntervalMillisCallbackProcess(30)
                .setPassIfAlreadyCompleted(false)
                .build();
        // 启动异步下载任务
        task.enqueue(mDownloadListener);

        // 取消任务
        task.cancel();
        // 同步启动下载任务
//        task.execute(mDownloadListener);
    }

    // 开启多个下载任务
    public void StartMultiTask() {
        DownloadTask[] tasks = new DownloadTask[2];
        tasks[0] = new DownloadTask.Builder(url0, "", "").build();
        tasks[1] = new DownloadTask.Builder(url1, "", "").build();
        // 开启多任务下载
        DownloadTask.enqueue(tasks, mDownloadListener);
    }

    // 下载任务监听
    private DownloadListener mDownloadListener = new DownloadListener() {
        @Override
        public void taskStart(@NonNull DownloadTask task) {

        }

        @Override
        public void connectTrialStart(@NonNull DownloadTask task, @NonNull Map<String, List<String>> requestHeaderFields) {

        }

        @Override
        public void connectTrialEnd(@NonNull DownloadTask task, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

        }

        @Override
        public void downloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info, @NonNull ResumeFailedCause cause) {

        }

        @Override
        public void downloadFromBreakpoint(@NonNull DownloadTask task, @NonNull BreakpointInfo info) {

        }

        @Override
        public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {

        }

        @Override
        public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

        }

        @Override
        public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {

        }

        @Override
        public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {

        }

        @Override
        public void fetchEnd(@NonNull DownloadTask task, int blockIndex, long contentLength) {

        }

        @Override
        public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {

        }
    };

}
