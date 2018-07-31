package com.zzy.def.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络相关
 */
public class NetUtils {

    /**
     * 判断网络是否可用
     * @return 当前网络状态
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivityManager) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            return null != info && info.isConnected() && info.getState() == NetworkInfo.State.CONNECTED;
        }
        return false;
    }

}
