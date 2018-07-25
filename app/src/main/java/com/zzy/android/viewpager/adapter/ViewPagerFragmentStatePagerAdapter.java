package com.zzy.android.viewpager.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 使用 FragmentStatePagerAdapter 时，当 Fragment 变得不可见，不仅视图层次销毁，实例也被销毁，即调用了 onDestroyView 和 onDestroy 方法，
 * 仅仅保存 Fragment 状态。相比而言， FragmentStatePagerAdapter 内存占用较小，所以适合大量动态页面，比如我们常见的新闻列表类应用。
 *
 * 当进入fragment3时，fragment1的声明周期：onPause\onStop\onDestroyView\onDestroy\onDetach，彻底销毁fragment1
 */
public class ViewPagerFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mList;

    public ViewPagerFragmentStatePagerAdapter(FragmentManager fm, @NonNull List<Fragment> list) {
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
