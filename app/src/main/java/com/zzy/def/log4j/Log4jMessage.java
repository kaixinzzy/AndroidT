package com.zzy.def.log4j;

import android.os.Environment;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by BCLZzy on 2017/8/11.
 * 输出Log属性控制
 * 用到的包：
 *      android-logging-log4j-1.0.3.jar
 *      log4j-1.2.17.jar
 * 权限：
 *      <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 *      <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 */
public class Log4jMessage {
    /**
     * 创建log4j日志文件
     */
    static Logger configLog(Class<?> cls, String projectName) {

        final LogConfigurator logConfigurator = new LogConfigurator();

        File dir = new File(Environment.getExternalStorageDirectory()
                + File.separator + "Log");
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }

        logConfigurator.setFileName(Environment.getExternalStorageDirectory()
                + File.separator + "Log" + File.separator + projectName + ".txt");

        // 設定Logger级别
        logConfigurator.setRootLevel(Level.ALL);
        logConfigurator.setLevel("org.apache", Level.ALL);

        // log的输出形式
        logConfigurator.setFilePattern("%d{MM-dd HH:mm:ss:SSS} %-5p [%c{2}]-[line:%L] %m%n");
        logConfigurator.setMaxFileSize(1024 * 512);//每个文件的Max大小，当前为512k
        logConfigurator.setMaxBackupSize(5);//同文件名称的文件保存最多5个

        logConfigurator.setImmediateFlush(true);
        logConfigurator.configure();

        return Logger.getLogger(cls);
    }
}
