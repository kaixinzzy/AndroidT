package com.zzy.android.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zzy.android.custom.seekbar.RangeSeekBar;
import com.zzy.event.ac.R;

public class SeekbarVActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private RangeSeekBar seekbar1,seekbar2,seekbar3;
    private String[] levelArrays = {"初级码农","中级码农","高级码农", "CTO","卒"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar_h);

        initView();
        seekbar1.setValue(-0.5f,0.8f);
        seekbar1.setIndicatorTextDecimalFormat("0.00");

        seekbar2.setIndicatorTextDecimalFormat("0");
        seekbar2.setIndicatorTextStringFormat("%s%%");
    }

    private void initView(){
        button = findViewById(R.id.button);
        seekbar1 = findViewById(R.id.seekbar1);
        seekbar2 = findViewById(R.id.seekbar2);
        seekbar3 = findViewById(R.id.seekbar3);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            finish();
        }
    }
}
