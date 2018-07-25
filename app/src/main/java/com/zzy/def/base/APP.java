package com.zzy.def.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.zzy.def.utils.CrashHandler;

/**
 * Created by BCLZzy on 2018/6/21.
 */

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLeakCanary();//初始化Activity内存泄漏检测工具LeakCanary

        CrashHandler.getInstance().init(this);//异常捕获
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    //初始化Activity内存泄漏检测工具LeakCanary
    private void initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

}
