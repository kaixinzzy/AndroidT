package com.zzy.android.contentprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import static com.zzy.android.contentprovider.ContentProviderCommon.DEFAULT_VALUE;
import static com.zzy.android.contentprovider.ContentProviderCommon.FILE_NAME_CONFIGURATION;
import static com.zzy.android.contentprovider.ContentProviderCommon.KEY;
import static com.zzy.android.contentprovider.ContentProviderCommon.METHOD_GET_STRING;
import static com.zzy.android.contentprovider.ContentProviderCommon.VALUE;

/**
 * SharedPreference内容解析器
 */
public class ContentResolverSP {
    private Uri uriSP = Uri.parse("content://com.zzy.provider.sharedpref");
    private ContentResolver contentResolver;

    public ContentResolverSP(Context context) {
        contentResolver = context.getContentResolver();
    }

    // 调用call方法，执行putString操作，无返回值
    public void putString() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY, "name");
        bundle.putString(VALUE, "郭森");
        contentResolver.call(uriSP, METHOD_GET_STRING, FILE_NAME_CONFIGURATION, bundle);
    }

    // 调用call方法，执行getString操作
    public void getString() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY, "name");
        bundle.putString(DEFAULT_VALUE, "");
        Bundle resultBundle = contentResolver.call(uriSP, METHOD_GET_STRING, FILE_NAME_CONFIGURATION, bundle);
        if (resultBundle != null) {
            resultBundle.getString("name", "");
        }
    }

}
