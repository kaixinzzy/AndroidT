package com.zzy.android.materialdesign;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.zzy.event.ac.R;

public class DesignActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;
    private FloatingActionButton mFloatingAB;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = findViewById(R.id.drawer);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);// 显示导航按钮
            actionBar.setHomeAsUpIndicator(R.drawable.shape_image_border);// 替换左侧返回按钮图标
        }

        mNavView = findViewById(R.id.nav_view);
        mNavView.setCheckedItem(R.id.nav_call);// 设置默认选中项
        mNavView.setNavigationItemSelectedListener(this);

        mFloatingAB = findViewById(R.id.fab);
        mFloatingAB.setOnClickListener(this);

        mRefreshLayout = findViewById(R.id.refresh);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);// 刷新进度条颜色
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override // Toolbar点击监听选项
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);// 打开侧滑菜单
                break;
            case R.id.revert:
                startActivity(new Intent(this, DesignDetailActivity.class));
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

    @Override // navigation点击监听选项
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_call:
                mDrawerLayout.closeDrawers();// 关闭侧滑菜单
                ToastUtils.showShort("You clicked nav_call");
                break;
            case R.id.nav_add:
                ToastUtils.showShort("You clicked nav_add");
                break;
            case R.id.nav_camera:
                ToastUtils.showShort("You clicked nav_camera");
                break;
            case R.id.nav_edit:
                ToastUtils.showShort("You clicked nav_edit");
                break;
            default:
        }
        return true;
    }

    @Override // FloatingActionButton点击监听选项
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                // Snackbar.LENGTH_SHORT        短时间显示
                // Snackbar.LENGTH_LONG         长时间显示
                // Snackbar.LENGTH_INDEFINITE   一直显示，只有当用户触发Action点击事件或手动删除时才会消失
                Snackbar snackbar = Snackbar.make(view, "您确定要删除本条数据吗?", Snackbar.LENGTH_SHORT)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ToastUtils.showShort("确定");
                            }
                        });
                snackbar.setActionTextColor(Color.YELLOW);// “确定”文字的颜色
                View v = snackbar.getView();
                v.setBackgroundColor(Color.RED);// 背景颜色
                TextView textView = v.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);// 提示文字颜色
                snackbar.show();
                break;
        }
    }

    @Override // 刷新数据监听
    public void onRefresh() {
        // 网络请求数据，并更新UI
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mRefreshLayout.setRefreshing(false);// 完成后，关闭加载进度条
        ToastUtils.showShort("数据刷新完成");
    }
}
