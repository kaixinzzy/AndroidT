package com.zzy.librarycommon.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.squareup.leakcanary.LeakCanary;
import com.zzy.librarycommon.utils.CrashHandler;
import com.zzy.librarycommon.utils.Density;

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Activity内存泄漏检测工具LeakCanary
        initLeakCanary();
        //异常捕获
        CrashHandler.getInstance().init(this);
        // 屏幕适配初始化
        Density.setDensity(this);
        // 屏幕适配使整个应用有效
        Density.setAppOrientation(true);
        // 工具集合
        Utils.init(this);
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
