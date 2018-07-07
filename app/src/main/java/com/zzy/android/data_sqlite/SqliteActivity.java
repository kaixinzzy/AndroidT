package com.zzy.android.data_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.zzy.event.ac.R;

public class SqliteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        MyDatabaseHelper dbHelper1 = new MyDatabaseHelper(this, "BookStore.db", null, 1);
        // 数据库不存在时，会调用DatabaseHelper的onCreate方法创建数据库和表
        // 数据库存在，并且版本号一致时，不调用任何方法
        // 数据库存在，但版本号不一致时，调用DatabaseHelper的onUpgrade数据库升级方法
        // 数据库版本降序程序崩溃
//        dbHelper1.getWritableDatabase();

        // 数据库升级
        MyDatabaseHelper dbHelper2 = new MyDatabaseHelper(this, "BookStore.db", null, 2);
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

    class MyDatabaseHelper extends SQLiteOpenHelper{
        private static final String TAG = "MyDatabaseHelper";
        private Context context;

        String CREATE_BOOK = "create table Book (" +
                "id integer primary key autoincrement, " +
                "author text, " +
                "price real, " +
                "pages integer, " +
                "name text)";
        String CREATE_CATEGORY = "create table Category (" +
                "id integer primary key autoincrement, " +
                "category_name text, " +
                "category_code integer)";

        /**
         *
         * @param context
         * @param name      数据库名称
         * @param factory
         * @param version   版本号
         */
        public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "onCreate");
            db.execSQL(CREATE_BOOK);// 创建数据表Book
            db.execSQL(CREATE_CATEGORY);// 创建数据表Category
            Toast.makeText(context, "数据表创建完成", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            Log.d(TAG, "onUpgrade");
            db.execSQL("drop table if exists Book");// 如果表Book存在，删除此表
            db.execSQL("drop table if exists Category");// 如果表Category存在，删除此表
            onCreate(db);
        }
    }
}
