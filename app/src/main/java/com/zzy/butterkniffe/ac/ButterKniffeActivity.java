package com.zzy.butterkniffe.ac;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zzy.event.ac.R;
import com.zzy.librarycommon.base.BaseActivity;

import butterknife.BindArray;
import butterknife.BindBitmap;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

public class ButterKniffeActivity extends BaseActivity {

    @BindView(R.id.bktv1)
    TextView bktv1;
    @BindView(R.id.bktv2)
    TextView bktv2;
    @BindView(R.id.bktv3)
    TextView bktv3;
    // 注解Resource中的string
    @BindString(R.string.app_name)
    String appName;
    // 注解Resource中的图片，取得Drawable
    @BindDrawable(R.drawable.ic_launcher_background)
    Drawable launcherDrawable;
    // 注解Resource中的图片，取得Bitmap
    @BindBitmap(R.drawable.ic_launcher_background)
    Bitmap launcherBitmap;
    // 注解Resource中string数组
    @BindArray(R.array.day_of_week)
    String weeks[];
    // 注解Resource中的color，获取int值
    @BindColor(R.color.colorAccent)
    int colorAccent;
    // 注解Resource中的dimen，取得float值
    @BindDimen(R.dimen.activity_horizontal_margin)
    float dimen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_kniffe);

    }

    // 注解点击事件
    @Optional
    @OnClick({R.id.bktv1, R.id.bktv2, R.id.bktv3})
    public void onButterKnifeClick(View view) {

    }
}
