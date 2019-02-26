package com.zzy.android.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zzy.android.custom.seekbar.OnRangeChangedListener;
import com.zzy.android.custom.seekbar.RangeSeekBar;
import com.zzy.android.custom.seekbar.VerticalRangeSeekBar;
import com.zzy.event.ac.R;

/**
 * ================================================
 * 作    者： zzy
 * 版    本：
 * 创建日期： 2019/2/19
 * 描    述: 进度条
 * ================================================
 */
public class SeekbarActivity extends AppCompatActivity implements OnRangeChangedListener {
    private static final String TAG = SeekbarActivity.class.getSimpleName();

    private VerticalRangeSeekBar mSeekBarCoffee;
    private VerticalRangeSeekBar mSeekBarSugar;
    private VerticalRangeSeekBar mSeekBarMilk;

    private float coffee = 2;// 咖啡量，默认值1
    private float sugar = 2;// 糖量，默认值0
    private float milk = 2;// 牛奶量，默认值0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar);

        mSeekBarCoffee = findViewById(R.id.seekbar_coffee);
        mSeekBarSugar = findViewById(R.id.seekbar_sugar);
        mSeekBarMilk = findViewById(R.id.seekbar_milk);

        mSeekBarCoffee.setValue(2);
        mSeekBarSugar.setValue(2);
        mSeekBarMilk.setValue(2);

        mSeekBarCoffee.setOnRangeChangedListener(this);
        mSeekBarSugar.setOnRangeChangedListener(this);
        mSeekBarMilk.setOnRangeChangedListener(this);
    }

    @Override
    public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {

        String name = "";
        if (view.getId() == R.id.seekbar_coffee) {
            name = "咖啡";
            coffee = leftValue;
        } else if (view.getId() == R.id.seekbar_sugar) {
            name = "糖";
            sugar = leftValue;
        } else if (view.getId() == R.id.seekbar_milk) {
            name = "牛奶";
            milk = leftValue;
        }
        Log.d(TAG, name + " leftValue:" + leftValue + " rightValue:" + rightValue);
    }

    @Override
    public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

    }

    @Override
    public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

    }
}
