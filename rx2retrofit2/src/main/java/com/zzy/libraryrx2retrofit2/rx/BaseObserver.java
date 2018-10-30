package com.zzy.libraryrx2retrofit2.rx;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;

import com.zzy.libraryrx2retrofit2.api.bean.HttpResult;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<HttpResult<T>> {
    private static final String TAG = BaseObserver.class.getSimpleName();

    protected Context mContext;

    // 返回成功
    protected abstract void onSuccess(HttpResult<T> t) throws Exception;
    // 返回失败
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    // 返回成功了，但是code错误
    protected void onCodeError(HttpResult<T> t) throws Exception {

    }

    protected void onRequestStart() {

    }

    protected void onRequestEnd() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(HttpResult<T> tHttpResult) {
        onRequestEnd();
        if (tHttpResult.getErrorCode() == 0) {
            try {
                onSuccess(tHttpResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            switch (tHttpResult.getErrorCode()) {
                case -1001:
                    Log.d(TAG, "登录失效，请重新登录。");
                    break;
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }
}
