package com.zzy.framework.mvc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zzy.event.ac.R;

import java.lang.ref.WeakReference;

public class MvcActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private ImageView mImageView;
    private ProgressDialog mProgressDialog;
    private static String image_path = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=112628966,1337456178&fm=27&gp=0.jpg";

    private final MyHandler mHandler = new MyHandler(this);
    private static class MyHandler extends Handler {

        private final WeakReference<MvcActivity> wr;
        public MyHandler(MvcActivity activity) {
            wr = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MvcActivity activity = wr.get();
            if (activity == null) {
                return;
            }
            switch (msg.what) {
                case 300:
                    int percent = activity.mProgressDialog.getProgress();
                    if (percent < 100) {
                        activity.mProgressDialog.setProgress(percent+1);
                        sendEmptyMessageDelayed(300, 100);
                    } else {
                        activity.mProgressDialog.dismiss();
                        Glide.with(activity.mContext).load(image_path).into(activity.mImageView);
                    }
                    break;
                case 404:
                    activity.mProgressDialog.dismiss();
                    Toast.makeText(activity.mContext, "Download fail !", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);
        init();
    }

    private void init() {
        mContext = this;
        mImageView = findViewById(R.id.imageView1);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancle", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mProgressDialog.dismiss();
            }
        });
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setTitle("下载文件");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                // 模拟下载图片
                mProgressDialog.show();
                mHandler.sendEmptyMessageDelayed(300, 100);
                break;
            case R.id.button2:
                mProgressDialog.dismiss();
                break;
        }
    }

}
