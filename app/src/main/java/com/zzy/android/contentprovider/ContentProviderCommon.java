package com.zzy.android.contentprovider;

public class ContentProviderCommon {
    // putString()方法标识
    public static final String METHOD_PUT_STRING = "put_string";
    // getString()方法标识
    public static final String METHOD_GET_STRING = "get_string";
    // putBoolean()方法标识
    public static final String METHOD_PUT_BOOLEAN = "put_boolean";
    // getBoolean()方法标识
    public static final String METHOD_GET_BOOLEAN = "get_boolean";
    // putFloat()方法标识
    public static final String METHOD_PUT_FLOAT = "put_float";
    // getFloat()方法标识
    public static final String METHOD_GET_FLOAT = "get_float";
    // putInt()方法标识
    public static final String METHOD_PUT_INT = "put_int";
    // getInt()方法标识
    public static final String METHOD_GET_INT = "get_int";
    // putLong()方法标识
    public static final String METHOD_PUT_LONG = "put_long";
    // getLong()方法标识
    public static final String METHOD_GET_LONG = "get_long";
    // putStringSet()方法标识
    public static final String METHOD_PUT_STRING_SET = "put_string_set";
    // getStringSet()方法标识
    public static final String METHOD_GET_STRING_SET = "get_string_set";
    // contains()方法标识
    public static final String METHOD_CONTAINS = "contains";
    // remove()方法标识
    public static final String METHOD_REMOVE = "remove";
    // clear()方法标识
    public static final String METHOD_CLEAR = "clear";
    // 多次putString(),一次提交方法标识
    public static final String METHOD_PUT_MULTIPLE_STRINGS = "put_multiple_strings";
    // 多次remove(),一次提交
    public static final String METHOD_REMOVE_SOME = "put_remove_some";

    public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final String DEFAULT_VALUE = "default_value";

    // 文件名
    public static final String FILE_NAME_RECORD = "com.zzy.record";
    public static final String FILE_NAME_CONFIGURATION = "com.zzy.configuration";
}
