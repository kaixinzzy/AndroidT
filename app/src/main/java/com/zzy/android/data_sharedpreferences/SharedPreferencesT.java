package com.zzy.android.data_sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by BCLZzy on 2018/7/6.
 * SharedPreferences
 * 以键值对的形式存入文件，方便读取
 * 适用：数据量小的一些明文配置文件
 */

public class SharedPreferencesT {

    public SharedPreferencesT(Context context) {
        // 只支持应用内部调用，为安全考虑。
        // 读取/data/data/<package name>/shared_prefs/sn.txt文件，如果不存在，则创建此文件。
        SharedPreferences sp = context.getSharedPreferences("sn", Context.MODE_PRIVATE);

        // 写入
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("name", "小白兔");
        edit.putBoolean("isBig", true);
        edit.apply();// 提交键值对

        // 读取 参数一：key 参数二：读取不到会的默认值
        String name = sp.getString("name", "我是默认值");
        boolean isBig = sp.getBoolean("isBig", false);

    }

}
