package com.zzy.android.viewpager.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zzy.event.ac.BR;

public class ViewStatus extends BaseObservable {

    private boolean mPageUpEnable;
    private boolean mPageDownEnable;

    @Bindable
    public boolean isPageUpEnable() {
        return mPageUpEnable;
    }

    public void setPageUpEnable(boolean pageUpEnable) {
        mPageUpEnable = pageUpEnable;
        notifyPropertyChanged(BR.pageUpEnable);
    }

    @Bindable
    public boolean isPageDownEnable() {
        return mPageDownEnable;
    }

    public void setPageDownEnable(boolean pageDownEnable) {
        mPageDownEnable = pageDownEnable;
        notifyPropertyChanged(BR.pageDownEnable);
    }
}
