package com.zzy.framework.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zzy.event.ac.R;

/**
 * MVP
 * https://juejin.im/post/58870cc2128fe1006c46e39c
 */
public class MvpActivity extends AppCompatActivity implements IDownloadView, View.OnClickListener {
    private Context mContext;
    private ImageView mImageView;
    private DownloadPresenter mDownloadPresenter;
    private ProgressDialog mProgressDialog;
    private static String image_path = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=112628966,1337456178&fm=27&gp=0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvc);
        mContext = this;
        mImageView = findViewById(R.id.imageView1);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        mDownloadPresenter = new DownloadPresenter(this);

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDownloadPresenter.onDestroy();
    }

    @Override // 显示/关闭 缓存图片进度ProgressBar
    public void showProgressBar(boolean show) {
        if (show) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }

    @Override // 更新进度值
    public void setProcessProgress(int progress) {
        mProgressDialog.setProgress(progress);
    }

    @Override // 图片下载完成，加载图片到ImageView
    public void setView(String result) {
        Glide.with(mContext).load(image_path).into(mImageView);
    }

    @Override // 图片缓存失败，Toast提示用户
    public void showFailToast() {
        Toast.makeText(mContext, "Download fail !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                // 开始缓存图片
                mDownloadPresenter.download(image_path);
                break;
            case R.id.button2:

                break;
        }
    }
}
