package com.zzy.framework.mvp;

/**
 * View Interface
 * View 接口定义所有需要实现的视图逻辑，在我们的下载任务中，视图逻辑包括
 *
 * 显示ProgressDialog
 * 显示Dialog具体进度
 * 显示具体的View（设置图片）
 * 显示错误信息（Toast提示）
 */
public interface IDownloadView {

    void showProgressBar(boolean show);// 显示进度条
    void setProcessProgress(int progress);// 设置进度条进度
    void setView(String result);// 根据数据设置view
    void showFailToast();// 设置请求失败是的view

}
