package com.zzy.framework.mvp;

/**
 * Presenter Interface
 * 连接Model和View的中间桥梁，需要将二者连接起来
 * 1\执行下载任务
 * 2\下载成功返回下载结果
 * 3\下载过程返回下载进度
 * 4\下载失败回调
 */
public interface IDownloadPresenter {
    void download(String url);// 下载
    void downloadSuccess(String result);// 下载成功
    void downloadProgress(int progress);// 当前下载进度
    void downloadFail();// 下载失败
    void onDestroy();// Activity被销毁
}
