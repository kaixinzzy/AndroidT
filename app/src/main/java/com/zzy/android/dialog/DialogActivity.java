package com.zzy.android.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.zzy.event.ac.R;

import java.util.Calendar;

/**
 * Dialog对话框
 * 参考：
 *      https://www.cnblogs.com/firebull/p/4967843.html
 */
public class DialogActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    // 普通的AlertDialog
    public void alertDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("title")// 标题
                .setMessage("Are you sure")// 提示消息
                .setIcon(R.drawable.ic_launcher_background);// 图标
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "which " + which);
            }
        });
        builder.setNeutralButton("帮助", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "which " + which);
            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "which " + which);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);// 是否响应BACK键，然后关闭dialog
        alertDialog.setCanceledOnTouchOutside(true);// 用户去摸dialog之外的空间是否关闭dialog
        alertDialog.show();
    }

    // list列表Dialog
    public void listDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("请选择喜欢的球员")
                .setIcon(R.drawable.ic_launcher_background);
        builder.setAdapter(new ArrayAdapter<String>(this, R.layout.alert_list_item, new String[]{"大罗","小罗","c罗"}),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "which " + which);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    // 自定义Dialog
    public void customDialog(View view) {
        DialogCustom dialog = new DialogCustom(this, "标题", "提示消息");
        // 确认按键
        dialog.setConfirmListener(new DialogCustom.ConfirmListener() {
            @Override
            public void onClick(Dialog dialog) {
                Log.d(TAG, "用户点击确定按钮");
                dialog.dismiss();
            }
        });
        // 取消按键
        dialog.setCancelListener(new DialogCustom.CancelListener() {
            @Override
            public void onClick(Dialog dialog) {
                Log.d(TAG, "用户点击取消按钮");
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    // 加载进度Dialog
    public void progressDialog(View view) {
        //如果只是简单的显示一下进度条，直接就可以用ProgressDialog的静态类
        //ProgressDialog.show(context, title, message);
        ProgressDialog mPd = new ProgressDialog(this);
        // ProgressDialog.STYLE_SPINNER---就是转圈,要显示进度可以自己setMessgae去设置
        // ProgressDialog.STYLE_HORIZONTAL---显示你setPorgess的值
        //mPd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mPd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mPd.setTitle("正在处理");
        //设置进度条显示的最大值
        mPd.setMax(100);
        mPd.setCancelable(true);
        mPd.setCanceledOnTouchOutside(false);
        mPd.show();
        // 一般progressDialog都会搭配一个后台的进程使用，下面的这个就是用AsyncTask来模拟一下更新的过程，
        // 更新使用mPd.setProgress或者setMessage来更新
//        mShowPbTask = new ShowPbTask();
//        mShowPbTask.execute();
    }

    // 日期选择Dialog
    public void datePickerDialog(View view) {
        Calendar ca = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                Log.d(TAG, "year="+year+",monthOfYear="+monthOfYear+",dayOfMonth="+dayOfMonth);
            }
        }, ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    // 时间选择Dialog
    public void timePickerDialog(View view) {
        Calendar caTime = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // TODO Auto-generated method stub
                Log.d(TAG, "hourOfDay="+hourOfDay+",minute="+minute);
            }
        }, caTime.get(caTime.HOUR_OF_DAY), caTime.get(caTime.MINUTE), true);
        timePickerDialog.show();
    }
}
