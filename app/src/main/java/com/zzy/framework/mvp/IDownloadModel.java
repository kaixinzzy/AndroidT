package com.zzy.framework.mvp;

/**
 * Model Interface
 * Model 接口定义所有需要实现的业务逻辑，在我们的下载任务中，业务逻辑只有一个，就是下载
 */
public interface IDownloadModel {
    /**
     * 下载操作
     * @param url
     */
    void download(String url);
    void onDestroy();// Activity被销毁

}
