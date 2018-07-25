package com.zzy.android.viewpager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.zzy.android.viewpager.adapter.ViewPagerAdapter;
import com.zzy.android.viewpager.adapter.ViewPagerFragmentPagerAdapter;
import com.zzy.android.viewpager.adapter.ViewPagerFragmentStatePagerAdapter;
import com.zzy.android.viewpager.fragment.Fragment1;
import com.zzy.android.viewpager.fragment.Fragment2;
import com.zzy.android.viewpager.fragment.Fragment3;
import com.zzy.android.viewpager.fragment.Fragment4;
import com.zzy.event.ac.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPager;

    private Button left;// 上一页
    private Button right;// 下一页
    private int position;// 当前状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        left = findViewById(R.id.button_left);
        right = findViewById(R.id.button_right);
        left.setOnClickListener(this);
        right.setOnClickListener(this);

        mViewPager = findViewById(R.id.viewpager);

//        initView();
        initFragment();
//        initFragmentState();

        mViewPager.addOnPageChangeListener(this);
        onPageSelected(0);// 解决初始化时，不调用onPageSelected无法得知当前状态问题
        mViewPager.setOffscreenPageLimit(3);// 设置viewpager左右预加载页数，默认值是1，这个值不能小于1
    }

    private void initFragmentState() {
        List<Fragment> mList = new ArrayList<>();
        mList.add(Fragment1.newInstance(null, null));
        mList.add(Fragment2.newInstance(null, null));
        mList.add(Fragment3.newInstance(null, null));
        mList.add(Fragment4.newInstance(null, null));

        ViewPagerFragmentStatePagerAdapter mAdapter = new ViewPagerFragmentStatePagerAdapter(getSupportFragmentManager(),mList);
        mViewPager.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();// 刷新数据,只对FragmentStatePagerAdapter生效
    }

    private void initFragment() {
        List<Fragment> mList = new ArrayList<>();
        mList.add(Fragment1.newInstance(null, null));
        mList.add(Fragment2.newInstance(null, null));
        mList.add(Fragment3.newInstance(null, null));
        mList.add(Fragment4.newInstance(null, null));

        ViewPagerFragmentPagerAdapter mAdapter = new ViewPagerFragmentPagerAdapter(getSupportFragmentManager(),mList);
        mViewPager.setAdapter(mAdapter);
    }

    // 初始化模拟数据
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.fragment_fragment1, null);
        @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.fragment_fragment2, null);
        List<View> mList = new ArrayList<>();
        mList.add(view1);
        mList.add(view2);

        ViewPagerAdapter mAdapter = new ViewPagerAdapter(mList);
        mViewPager.setAdapter(mAdapter);
    }

    // ViewPager滑动监听 ----------------------------------------------------------------------------

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 记录当前选中页面position值
        this.position = position;
        // 左右滑动按钮的显示or隐藏
        right.setVisibility(View.INVISIBLE);
        left.setVisibility(View.INVISIBLE);
        if (mViewPager.getAdapter() == null) {
            return;
        }
        if (mViewPager.getAdapter().getCount() <= 1) {
            return;
        }
        if (position != 0) {
            left.setVisibility(View.VISIBLE);
        }
        if (position != mViewPager.getAdapter().getCount()-1) {
            right.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_left:
                mViewPager.setCurrentItem(position-1);// 滑动到上一页
                break;
            case R.id.button_right:
                mViewPager.setCurrentItem(position+1);// 滑动到下一页
                break;
        }
    }
}
