package com.zzy.android.viewpager.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 使用 FragmentPagerAdapter 时，ViewPager 中的所有 Fragment 实例常驻内存，当 Fragment 变得不可见时仅仅是视图结构的销毁，
 * 即调用了 onDestroyView 方法。由于 FragmentPagerAdapter 内存消耗较大，所以适合少量静态页面的场景。
 *
 * 当进入fragment3时，fragment1的声明周期：onPause\onStop\onDestroyView，并没有彻底销毁
 */
public class ViewPagerFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList;

    public ViewPagerFragmentPagerAdapter(FragmentManager fm, @NonNull List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
