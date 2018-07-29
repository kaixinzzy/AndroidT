package com.zzy.android.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * 懒加载（只加载UI，暂不加载网络数据。等这个页面真正显示时，在加载这个页面的数据，并刷新界面）
 * 在ViewPager中，多个Fragment如果同时加载网络数据，可能造成界面显示变慢。
 * https://blog.csdn.net/aiynmimi/article/details/73277836
 */
public abstract class LazyLoadFragment extends Fragment {
    protected boolean isViewInitiated;// UI是否加载王城
    protected boolean isVisibleToUser;// 当前页面是否正在显示
    protected boolean isDataInitiated;// 数据是否加载过

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override // UI可见时，会调用此方法，isVisibleToUser=true
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    // 懒加载网络数据，并刷新UI
    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

}
