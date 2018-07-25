package com.zzy.android.viewpager.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<View> mList;

    public ViewPagerAdapter(@NonNull List<View> list) {
        this.mList = list;
    }

    @Override // 显示view的总数量
    public int getCount() {
        return mList.size();
    }

    @NonNull
    @Override // 每次滑动的时候，生成view
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mList.get(position));
        return container.getChildAt(position);
    }

    @Override // 滑动切换的时候，销毁当前的view
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mList.get(position));
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // 官方建议写法
        return view == object;
    }

}
