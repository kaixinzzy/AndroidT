package com.zzy.android.data_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by BCLZzy on 2018/7/9.
 */

public class DatabaseHelperT extends SQLiteOpenHelper {
    private static final String TAG = "MyDatabaseHelper";
    private Context context;

    private String CREATE_BOOK = "create table Book (" +
            "id integer primary key autoincrement, " +
            "author text, " +
            "price real, " +
            "pages integer, " +
            "name text)";
    private String CREATE_CATEGORY = "create table Category (" +
            "id integer primary key autoincrement, " +
            "category_name text, " +
            "category_code integer)";

    /**
     *
     * @param context   上下文
     * @param name      数据库名称
     * @param factory   允许我们在查询数据的时候返回一个自定义的Cursor，一般都传入null
     * @param version   当前数据库的版本号【1、必须大于0，否则报错 2、系统会根据这个版本号，来判断是佛进行升级or降级操作】
     *                  当无此数据库时，会调用onCreate方法，创建数据库
     *                  当有此数据库，传入版本号大于本地版本号时，调用onUpgrade方法，进行升级数据库
     *                  当有此数据库，如初版本号小余本地版本号时，调用onDowngrade方法，进行降级数据库
     */
    DatabaseHelperT(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    /**
     * 创建数据库
     * @param db db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        db.execSQL(CREATE_BOOK);// 创建数据表Book
        db.execSQL(CREATE_CATEGORY);// 创建数据表Category
        Toast.makeText(context, "数据表创建完成", Toast.LENGTH_LONG).show();
    }

    /**
     * 数据库升级
     * @param db db
     * @param oldVersion 老版本号
     * @param newVersion 新版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
        db.execSQL("drop table if exists Book");// 如果表Book存在，删除此表
        db.execSQL("drop table if exists Category");// 如果表Category存在，删除此表
        onCreate(db);// 调用onCreate方法
    }

    /**
     * 数据库降级
     * @param db db
     * @param oldVersion 老版本号
     * @param newVersion 新版本号
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
