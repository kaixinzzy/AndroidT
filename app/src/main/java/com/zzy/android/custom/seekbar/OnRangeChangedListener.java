package com.zzy.android.custom.seekbar;

/**
 * ================================================
 * 作    者： zzy
 * 版    本：
 * 创建日期： 2019/2/19
 * 描    述:
 * ================================================
 */
public interface OnRangeChangedListener {

    void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser);

    void onStartTrackingTouch(RangeSeekBar view, boolean isLeft);

    void onStopTrackingTouch(RangeSeekBar view, boolean isLeft);

}
