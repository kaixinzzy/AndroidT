package com.zzy.android.data_sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by BCLZzy on 2018/7/6.
 * SharedPreferences
 * 以键值对的形式存入文件，方便读取
 * 适用：数据量小的一些明文配置文件 - 勿存储大量数据，因为这些数据都会缓存到内存中，容易引起内存溢出
 */

public class SharedPreferencesT {

    public SharedPreferencesT(Context context) {
        // 只支持应用内部调用，为安全考虑。
        // 读取/data/data/<package name>/shared_prefs/sn.txt文件，如果不存在，则创建此文件。
        SharedPreferences sp = context.getSharedPreferences("sn", Context.MODE_PRIVATE);

        // 写入
        SharedPreferences.Editor edit = sp.edit();
        // 支持基本数据类型 float、int、long、boolean
        // 支持 String、Set
        edit.putString("name", "小白兔");// 添加 or 替换已有字段
        edit.apply();// 提交键值对，无返回值，异步效率高【不需要返回值，并且单进程中使用】
        boolean commit = edit.commit();// 提交键值对，返回boolean表明修改是否提交成功，同步效率差【需要返回值 or 多进程】

        // 清空文件，并提交新数据
        edit.clear();
        edit.putBoolean("isBig", true);
        edit.commit();

        // 删除字段
        edit.remove("name");
        edit.commit();

        // 读取 所以的getXXX()方法，都支持默认值，即如果没有找到与当前key值对应的value，则返回我们自己设置的默认值
        String name = sp.getString("name", "我是默认值");
        boolean isBig = sp.getBoolean("isBig", false);
        Map<String, ?> map = sp.getAll();// 获取所有数据
        boolean containsName = sp.contains("name");// 是否包含key为name的键值对

    }

}
