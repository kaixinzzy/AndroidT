package com.zzy.android.data_sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zzy.event.ac.R;

public class SqliteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        DatabaseHelperT dbHelper1 = new DatabaseHelperT(this, "BookStore.db", null, 1);
        // 数据库不存在时，会调用DatabaseHelper的onCreate方法创建数据库和表
        // 数据库存在，并且版本号一致时，不调用任何方法
        // 数据库存在，但版本号不一致时，调用DatabaseHelper的onUpgrade数据库升级方法
        // 数据库版本降序程序崩溃
//        dbHelper1.getWritableDatabase();

        // 数据库升级
        DatabaseHelperT dbHelper2 = new DatabaseHelperT(this, "BookStore.db", null, 2);
        // 此时BookStore.db数据库已经存在，但版本号不一致，会调用DatabaseHelper的onUpgrade数据库升级方法
        SQLiteDatabase db = dbHelper2.getWritableDatabase();

        // 插入数据
        ContentValues values = new ContentValues();
        values.put("name", "第一行代码");
        values.put("author", "郭森");
        values.put("pages", 570);
        values.put("price", 28.86);
        db.insert("Book", null, values);

        // 更新数据
        values.clear();
        values.put("price","10.68");// 更新的数据
        String where = "name = ?";// 条件
        String[] args = {"第一行代码"};// 条件
        db.update("Book",values, where, args);

        // 删除数据
        db.delete("Book","pages > ?",new String[]{"500"});

        // 查询数据
        String tableName = "Book";// 数据表
        String[] columns = new String[]{"author","name","price"};// 获取的列
        String whereQ = "name = ?";// 条件
        String[] argsQ = {"第一行代码"};// 条件
        Cursor cursor = db.query(tableName, columns, whereQ, argsQ, "pages", null, "des");
        if (null != cursor && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));
            } while (cursor.moveToNext());
        }
        if (null != cursor) {
            cursor.close();
        }
    }
}
