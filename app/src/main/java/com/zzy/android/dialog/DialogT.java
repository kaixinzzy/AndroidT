package com.zzy.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zzy.event.ac.R;

public class DialogT extends Dialog {


    public DialogT(@NonNull Context context) {
        super(context, R.style.AutomatDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
