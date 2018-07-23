package com.zzy.android.contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * 数据库内容解析器
 */
public class ContentResolverDB {

    public ContentResolverDB(Context context) {
        Uri uri = Uri.parse("content://com.zzy.provider");
        Uri uriDB = Uri.parse("content://com.zzy.provider.db");


        ContentResolver contentResolver = context.getContentResolver();


        // 插入数据
        ContentValues values = new ContentValues();
        values.put("name", "第一行代码");
        values.put("author", "郭森");
        values.put("pages", 570);
        values.put("price", 28.86);
        contentResolver.insert(uri, values);

        // 更新数据
        values.clear();
        values.put("price","10.68");// 更新的数据
        String where = "name = ?";// 条件
        String[] args = {"第一行代码"};// 条件
        contentResolver.update(uri, values, where, args);

        // 删除数据
        contentResolver.delete(uri,"pages > ?",new String[]{"500"});

        // 查询
        String[] projection = new String[]{"author","name","price"};// 获取的列
        String whereQ = "name = ?";// 条件
        String[] argsQ = {"第一行代码"};// 条件
        String sortOrder = "";//排序
        Cursor cur = contentResolver.query(uri, projection, whereQ, argsQ, null);
        if (null != cur && cur.moveToFirst()) {
            do {
                String name = cur.getString(cur.getColumnIndex("name"));
                double price = cur.getDouble(cur.getColumnIndex("price"));
            } while (cur.moveToNext());
        }
        if (null != cur) {
            cur.close();
        }
    }

}
