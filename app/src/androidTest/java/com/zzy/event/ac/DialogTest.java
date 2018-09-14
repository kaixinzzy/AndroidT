package com.zzy.event.ac;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.zzy.android.dialog.DialogT;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DialogTest {

    @Test
    public void t() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DialogT dialogT = new DialogT(appContext);

    }

}
