package com.zzy.android.handler;

import android.os.AsyncTask;

/**
 * 异步任务
 * 调用：
 *      new AsyncTaskT().execute();
 */
public class AsyncTaskT extends AsyncTask<String, Integer, Boolean> {

    // 执行任务执行做一些事，运行在主线程
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // 异步耗时逻辑写在这里
    @Override
    protected Boolean doInBackground(String... strings) {
        // 执行下载任务
        // ...
        // 通知更新进度条
        publishProgress(1);
        // 下载成功，返回true
        return true;
    }

    // 更新进度条
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    // 对最后结果的处理，运行在主线程
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
