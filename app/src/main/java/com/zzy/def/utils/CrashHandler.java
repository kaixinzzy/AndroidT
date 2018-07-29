package com.zzy.def.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.zzy.def.log4j.LogUtil;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by BCLZzy on 2017/8/11.
 * 异常捕获
 * 初始化：
 *      最好在Application中初始化
 *      CrashHandler.getInstance().init(this);
 * 注意事项：
 *      有时候我们捕获不到异常，是用于调用了第三方jar包，他们把异常捕获了。
 *      解决方法：等第三方初始化完成后，延迟几秒我们的CrashHandler再启动。
 *
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler  {

    private static final String TAG = "CrashHandler";
//    private static final Logger LOGGER = LogUtil.getLogger(CrashHandler.class);

    @SuppressLint("StaticFieldLeak")
    private static CrashHandler mCrashHandler;
    private Context mContext;

    public void init(Context context) {
        mContext = context.getApplicationContext();
        //设置该CrashHandler为系统默认的
        Thread.setDefaultUncaughtExceptionHandler(mCrashHandler);
    }

    public static synchronized CrashHandler getInstance(){
        if (null == mCrashHandler){
            mCrashHandler = new CrashHandler();
        }
        return mCrashHandler;
    }

    //私有构造方法
    private CrashHandler(){}

    //捕获异常
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        final String errorinfo = getErrorInfo(ex);
        Log.d(TAG, errorinfo);
//        LOGGER.debug(errorinfo);

        Log.d(TAG, "异常日志上传接口");
        new Thread() {
            @Override
            public void run() {
                uploadErrorInfo(errorinfo);
            }
        }.start();

        Log.d(TAG, "关闭应用");
        ActivityManagerT.getInstance().Exit(mContext);

    }

    //异常上传服务器
    private void uploadErrorInfo(String errorinfo) {

    }

    /**
     * 解析错误信息
     */
    private String getErrorInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        pw.close();
        return writer.toString();
    }

}
