package com.zzy.event.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.zzy.event.MessageEvent;
import com.zzy.event.MessageEventSticky;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Main2Activity extends AppCompatActivity {
    TextView sticky_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sticky_textview = findViewById(R.id.sticky_textview);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().removeAllStickyEvents();//移除所有粘性事件
        EventBus.getDefault().removeStickyEvent(MessageEventSticky.class);//移除指定粘性事件
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // 粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessageEventSticky(MessageEventSticky sticky) {
        sticky_textview.setText(sticky.getName());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_eventbus_post:
                EventBus.getDefault().post(new MessageEvent("张三", "123456"));
                break;
        }
    }
}
