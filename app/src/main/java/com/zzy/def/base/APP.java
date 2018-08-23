package com.zzy.def.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.squareup.leakcanary.LeakCanary;
import com.zzy.def.utils.CrashHandler;
import com.zzy.def.utils.Density;

/**
 * Created by BCLZzy on 2018/6/21.
 */

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLeakCanary();//初始化Activity内存泄漏检测工具LeakCanary
        CrashHandler.getInstance().init(this);//异常捕获
        Density.setDensity(this);// 屏幕适配初始化
        Density.setAppOrientation(true);// 屏幕适配使整个应用有效
        Utils.init(this);// 工具集合
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
