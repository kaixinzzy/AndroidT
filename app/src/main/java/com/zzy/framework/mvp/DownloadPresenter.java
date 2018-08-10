package com.zzy.framework.mvp;

/**
 * DownloadPresenter的构造方法中，同时实例化了Model和View，这样Presenter中就同时包含了两者；
 * 这样；在Presenter具体实现中，业务相关的操作由Model去完成（例如download），视图相关的操作由View去完成
 * Presenter 作为桥梁的作用就这样体现出来了，巧妙的将View和Model的具体实现连接了起来。
 */
public class DownloadPresenter implements IDownloadPresenter {
    private IDownloadView mIDownloadView;
    private IDownloadModel mIDownloadModel;

    public DownloadPresenter(IDownloadView iDownloadView) {
        mIDownloadView = iDownloadView;
        mIDownloadModel = new DownloadModel(this);
    }

    @Override // 开始缓存图片
    public void download(String url) {
        mIDownloadView.showProgressBar(true);// 通知View显示进度条
        mIDownloadModel.download(url);// 通知Model执行图片缓存任务
    }

    @Override // 缓存完成
    public void downloadSuccess(String result) {
        mIDownloadView.showProgressBar(false); // 通知View关闭进度条
        mIDownloadView.setView(result);// 通知View将图片现在到ImageView上
    }

    @Override // 更新缓存进度
    public void downloadProgress(int progress) {
        mIDownloadView.setProcessProgress(progress);// 通知View更新缓存进度
    }

    @Override // 图片缓存失败
    public void downloadFail() {
        mIDownloadView.showProgressBar(false);
        mIDownloadView.showFailToast();
    }

    @Override
    public void onDestroy() {
        mIDownloadModel.onDestroy();
    }
}
