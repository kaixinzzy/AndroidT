package com.zzy.android.viewpager.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zzy.event.ac.BR;

public class Loading extends BaseObservable {
    private boolean loading;

    public Loading(boolean loading) {
        this.loading = loading;
    }

    @Bindable
    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyPropertyChanged(BR.loading);
    }
}
