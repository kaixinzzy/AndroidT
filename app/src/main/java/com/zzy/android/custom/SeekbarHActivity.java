package com.zzy.android.custom;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zzy.android.custom.seekbar.OnRangeChangedListener;
import com.zzy.android.custom.seekbar.RangeSeekBar;
import com.zzy.event.ac.R;

public class SeekbarHActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private RangeSeekBar seekbar1, seekbar2, seekbar3, seekbar4, seekbar5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar_v);
        initView();

        seekbar2.setTypeface(Typeface.DEFAULT_BOLD);
        seekbar2.getLeftSeekBar().setTypeface(Typeface.DEFAULT_BOLD);
        seekbar2.setIndicatorTextDecimalFormat("0.00");
        seekbar4.setIndicatorTextDecimalFormat("0");
        seekbar5.setIndicatorTextStringFormat("你是%s吗");

        seekbar1.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                seekbar1.setIndicatorText((int)leftValue+"");
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view,  boolean isLeft) {
                //do what you want!!
            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view,  boolean isLeft) {
                //do what you want!!
            }
        });
        seekbar2.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {
                //do what you want!!
            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {
                //do what you want!!
            }
        });

        seekbar4.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                if (leftValue <= 50){
                    view.setProgressColor(getResources().getColor(R.color.colorAccent));
                    view.getLeftSeekBar().setThumbDrawableId(R.drawable.thumb_activated);
                    view.getLeftSeekBar().setIndicatorBackgroundColor(getResources().getColor(R.color.colorAccent));
                }else {
                    view.setProgressColor(getResources().getColor(R.color.colorPrimary));
                    view.getLeftSeekBar().setIndicatorBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    view.getLeftSeekBar().setThumbDrawableId(R.drawable.thumb_blue);
                }
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        seekbar5.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {

            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        seekbar1.setValue(90);
        seekbar2.setValue(-0.5f,0.8f);
        seekbar3.setValue(-26, 90);
        seekbar5.setValue(25, 75);
    }

    private void initView(){
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayShowCustomEnabled(true);
//            getSupportActionBar().setCustomView(R.layout.layout_action_bar);
//            getSupportActionBar().getCustomView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//        }

        button = findViewById(R.id.button);
        seekbar1 = findViewById(R.id.seekbar1);
        seekbar2 = findViewById(R.id.seekbar2);
        seekbar3 = findViewById(R.id.seekbar3);
        seekbar4 = findViewById(R.id.seekbar4);
        seekbar5 = findViewById(R.id.seekbar5);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            startActivity(new Intent(SeekbarHActivity.this, SeekbarVActivity.class));
        }
    }
}
