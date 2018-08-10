package com.zzy.framework.mvp;

import android.os.Handler;
import android.os.Message;

public class DownloadModel implements IDownloadModel {
    private IDownloadPresenter mIDownloadPresenter;
    private MyHandler mHandler;

    public DownloadModel(IDownloadPresenter iDownloadPresenter) {
        this.mIDownloadPresenter = iDownloadPresenter;
        mHandler = new MyHandler();
    }

    @Override
    public void download(String url) {
        mHandler.sendEmptyMessageDelayed(300, 100);
    }

    @Override
    public void onDestroy() {
        if (null != mHandler) {
            //清空消息队列
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    class MyHandler extends Handler {
        private int i = 0;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 300:
                    if (i < 100) {
                        mIDownloadPresenter.downloadProgress(i++);
                        sendEmptyMessageDelayed(300, 100);
                    } else {
                        mIDownloadPresenter.downloadSuccess("下载完成了，缓存图片的路径是xxx");
                    }
                    break;
                case 404:
                    mIDownloadPresenter.downloadFail();
                    break;
                default:
                    break;
            }
        }
    }

}
