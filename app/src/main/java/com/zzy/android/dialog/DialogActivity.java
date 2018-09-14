package com.zzy.android.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zzy.event.ac.R;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        DialogT dialogT = new DialogT(this);
    }
}
