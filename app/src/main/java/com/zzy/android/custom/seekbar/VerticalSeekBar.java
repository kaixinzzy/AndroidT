package com.zzy.android.custom.seekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class VerticalSeekBar extends android.support.v7.widget.AppCompatSeekBar {

    public VerticalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //将SeekBar转转90度
        canvas.rotate(-90);
        //将旋转后的视图移动回来
        canvas.translate(-getHeight(),0);
        Log.i("getHeight()",getHeight()+"");
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                int i=0;
                //获取滑动的距离
                i=getMax() - (int) (getMax() * event.getY() / getHeight());
                //设置进度
                setProgress(i);
                Log.i("Progress",getProgress()+"");
                //每次拖动SeekBar都会调用
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                Log.i("getWidth()",getWidth()+"");
                Log.i("getHeight()",getHeight()+"");
                break;

            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
//        return super.onTouchEvent(event);
    }
}
