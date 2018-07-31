package com.zzy.android.materialdesign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.zzy.event.ac.R;

/**
 * 状态栏透明没有写，具体代码可参考郭森第一行代码中的讲解。
 */
public class DesignDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private CollapsingToolbarLayout mCtl;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_detail);
        mToolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);// 显示导航按钮
//            actionBar.setHomeAsUpIndicator();// 替换左侧返回按钮图标
        }

        mCtl = findViewById(R.id.detail_collapsing_toolbar);
        mCtl.setTitle("我是标题");
        mCtl.setExpandedTitleColor(Color.RED);// 扩张时title的颜色
        mCtl.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);// 扩张时title对齐方式

        mCtl.setCollapsedTitleTextColor(Color.WHITE);// 收缩时title的颜色
        mCtl.setCollapsedTitleGravity(Gravity.START); // 收缩时title对齐方式

        mFab = findViewById(R.id.detail_floating_action_button);
        mFab.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override // Toolbar点击监听选项
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.revert:
                startActivity(new Intent(this, DesignActivity.class));
                ToastUtils.showShort("You clicked revert");
                break;
            case R.id.delete:
                ToastUtils.showShort("You clicked delete");
                break;
            case R.id.manage:
                ToastUtils.showShort("You clicked manage");
                break;
            default:
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_floating_action_button:
                ToastUtils.showShort("点我干嘛？");
                break;
        }
    }
}
