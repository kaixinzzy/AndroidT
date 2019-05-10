package com.zzy.librarycommon.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.zzy.librarycommon.utils.CrashHandler;
import com.zzy.librarycommon.utils.Density;

public class APP extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 方法数超过64K分包
        MultiDex.install(this);
    }

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
        // Logger
        PrettyFormatStrategy ps = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)// 是否显示线程信息，默认为ture
                .methodCount(0)       // 显示的方法行数，默认为2
                .methodOffset(5)      // 隐藏内部方法调用到偏移量，默认为5

                .tag("zzylog")           // 每个日志的全局标记。默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(ps));
        //SharedPreferences 数据缓存
        Hawk.init(this).build();
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
