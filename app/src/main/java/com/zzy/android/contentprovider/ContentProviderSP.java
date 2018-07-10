package com.zzy.android.contentprovider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by BCLZzy on 2018/7/9.
 * 内容提供器 - 分享SharedPreference
 */

public class ContentProviderSP extends ContentProviderBase {
    private static final String TAG = "ContentProviderSP";
    private static SharedPreferences spRecord;
    private static SharedPreferences spConfiguration;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            spRecord = context.getSharedPreferences("com.zzy.record", Context.MODE_PRIVATE);
            spConfiguration = context.getSharedPreferences("com.zzy.configuration", Context.MODE_PRIVATE);
        }
        return false;
    }

    public static SharedPreferences getSpRecord() {
        return spRecord;
    }

    public static SharedPreferences getSpConfiguration () {
        return spConfiguration;
    }

    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        return super.call(method, arg, extras);
    }


}
