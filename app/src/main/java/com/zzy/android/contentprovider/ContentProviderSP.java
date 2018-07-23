package com.zzy.android.contentprovider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.zzy.android.contentprovider.ContentProviderCommon.DEFAULT_VALUE;
import static com.zzy.android.contentprovider.ContentProviderCommon.FILE_NAME_CONFIGURATION;
import static com.zzy.android.contentprovider.ContentProviderCommon.FILE_NAME_RECORD;
import static com.zzy.android.contentprovider.ContentProviderCommon.KEY;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_CLEAR;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_CONTAINS;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_GET_BOOLEAN;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_GET_FLOAT;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_GET_INT;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_GET_LONG;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_GET_STRING;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_GET_STRING_SET;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_PUT_BOOLEAN;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_PUT_FLOAT;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_PUT_INT;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_PUT_LONG;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_PUT_MULTIPLE_STRINGS;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_PUT_STRING;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_PUT_STRING_SET;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_REMOVE;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_REMOVE_SOME;
import static com.zzy.android.contentprovider.ContentProviderCommon.VALUE;

/**
 * Created by BCLZzy on 2018/7/9.
 * 内容提供器 - 分享SharedPreference
 * https://blog.csdn.net/zhangyongfeiyong/article/details/51860572
 */

public class ContentProviderSP extends ContentProviderBase {
    private static final String TAG = "ContentProviderSP";
    private static SharedPreferences spRecord;
    private static SharedPreferences spConfiguration;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            spRecord = context.getSharedPreferences(FILE_NAME_RECORD, Context.MODE_PRIVATE);
            spConfiguration = context.getSharedPreferences(FILE_NAME_CONFIGURATION, Context.MODE_PRIVATE);
        }
        return false;
    }

    public static SharedPreferences getSpRecord() {
        return spRecord;
    }

    public static SharedPreferences getSpConfiguration () {
        return spConfiguration;
    }

    /**
     *
     * @param method 调用SharedPreference中的方法名，比如getString、getInt、getBoolean
     * @param arg    执行操作的文件对象
     * @param extras 操作的键值对
     * @return Bundle
     */
    @SuppressWarnings("NullableProblems")
    @Nullable
    @Override
    public Bundle call(@NonNull String method, @Nullable String arg, @Nullable Bundle extras) {
        Bundle bundle = null;
        SharedPreferences sp;
        if (TextUtils.isDigitsOnly(arg)) {
            Log.d(TAG, "arg is empty");
            return null;
        }
        if (TextUtils.isEmpty(method)) {
            Log.d(TAG, "method is empty");
            return null;
        }
        // 匹配操作文件
        if (FILE_NAME_RECORD.equals(arg)) {
            sp = spRecord;
        } else if (FILE_NAME_CONFIGURATION.equals(arg)) {
            sp = spConfiguration;
        } else {
            Log.d(TAG, "arg is illegal");
            return null;
        }
        if (extras == null) {
            Log.d(TAG, "extras is null");
            return null;
        }
        // 对数据的操作
        try {
            switch (method) {
                case METHOD_PUT_STRING:// putString
                {
                    String key = extras.getString(KEY);
                    String value = extras.getString(VALUE);
                    sp.edit().putString(key, value).apply();// 防止执行时间过长，这里用apply方法提交
                    break;
                }
                case METHOD_GET_STRING:// getString
                {
                    String key = extras.getString(KEY);
                    String defValue = extras.getString(DEFAULT_VALUE);
                    String value = sp.getString(key, defValue);
                    bundle = new Bundle();
                    bundle.putString(VALUE, value);
                    break;
                }
                case METHOD_PUT_BOOLEAN:// putBoolean
                {
                    String key = extras.getString(KEY);
                    boolean value = extras.getBoolean(VALUE);
                    sp.edit().putBoolean(key, value).apply();
                    break;
                }
                case METHOD_GET_BOOLEAN:// getBoolean
                {
                    String key = extras.getString(KEY);
                    boolean defValue = extras.getBoolean(DEFAULT_VALUE);
                    boolean value = sp.getBoolean(key, defValue);
                    bundle = new Bundle();
                    bundle.putBoolean(VALUE, value);
                }
                case METHOD_PUT_FLOAT:// putFloat
                {
                    String key = extras.getString(KEY);
                    float value = extras.getFloat(VALUE);
                    sp.edit().putFloat(key, value).apply();
                }
                case METHOD_GET_FLOAT:// getFloat
                {
                    String key = extras.getString(KEY);
                    float defValue = extras.getFloat(DEFAULT_VALUE);
                    float value = sp.getFloat(key, defValue);
                    bundle = new Bundle();
                    bundle.putFloat(VALUE, value);
                }
                case METHOD_PUT_INT:// putInt
                {
                    String key = extras.getString(KEY);
                    int value = extras.getInt(VALUE);
                    sp.edit().putInt(key, value).apply();
                }
                case METHOD_GET_INT:// getInt
                {
                    String key = extras.getString(KEY);
                    int defValue = extras.getInt(DEFAULT_VALUE);
                    int value = sp.getInt(key, defValue);
                    bundle = new Bundle();
                    bundle.putInt(VALUE, value);
                }
                case METHOD_PUT_LONG:// putLong
                {
                    String key = extras.getString(KEY);
                    Long value = extras.getLong(VALUE);
                    sp.edit().putLong(key, value).apply();
                }
                case METHOD_GET_LONG:// getLong
                {
                    String key = extras.getString(KEY);
                    Long defValue = extras.getLong(DEFAULT_VALUE);
                    Long value = sp.getLong(key, defValue);
                    bundle = new Bundle();
                    bundle.putLong(VALUE, value);
                }
                case METHOD_PUT_STRING_SET:// putString Set
                {
                    String key = extras.getString(KEY);
                    Set<String> value = null;
                    String[] array = extras.getStringArray(KEY);
                    if (null != array) {
                        value = new HashSet<>(Arrays.asList(array));
                    }
                    if (null != value) {
                        sp.edit().putStringSet(key, value).apply();
                    } else {
                        Log.e(TAG, "StringSet value is null");
                    }
                    break;
                }
                case METHOD_GET_STRING_SET:// getString Set
                {
                    String key = extras.getString(KEY);
                    Set<String> defValue = null;
                    String[] array = extras.getStringArray(KEY);
                    if (null != array) {
                        defValue = new HashSet<>(Arrays.asList(array));
                    }
                    Set<String> oldValue = sp.getStringSet(key, defValue);
                    Set<String> value = new HashSet<>(null == oldValue ? new HashSet<String>() : oldValue);
                    bundle = new Bundle();
                    bundle.putStringArray(VALUE, value.toArray(new String[value.size()]));
                    break;
                }
                case METHOD_CONTAINS:// contains
                {
                    String key = extras.getString(KEY);
                    boolean value = sp.contains(key);
                    bundle = new Bundle();
                    bundle.putBoolean(VALUE, value);
                    break;
                }
                case METHOD_REMOVE:// remove
                {
                    String key = extras.getString(KEY);
                    sp.edit().remove(key).apply();
                    break;
                }
                case METHOD_CLEAR:// clear
                {
                    sp.edit().clear().apply();
                    break;
                }
                case METHOD_PUT_MULTIPLE_STRINGS:// 多次putString，一次提交
                {
                    SharedPreferences.Editor editor = sp.edit();
                    for (String key: extras.keySet()) {
                        String value = extras.getString(key);
                        editor.putString(key, value);
                    }
                    editor.apply();
                    break;
                }
                case METHOD_REMOVE_SOME:// 多次remove，一次提交
                {
                    SharedPreferences.Editor editor = sp.edit();
                    String[] array = extras.getStringArray(KEY);
                    assert array != null;
                    for (String key: array) {
                        editor.remove(key);
                    }
                    editor.apply();
                    break;
                }
                default:

                    break;

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        // 将获取到的值返回给调用方，若为put操作，bundle则为null
        return bundle;
    }


}
