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
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        //移除所有粘性事件
        //EventBus.getDefault().removeAllStickyEvents();
        //移除指定粘性事件
        EventBus.getDefault().removeStickyEvent(MessageEventSticky.class);
        // 注销EventBus
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // 订阅粘性事件
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessageEventSticky(MessageEventSticky sticky) {
        sticky_textview.setText(sticky.getName());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_eventbus_post:
                // 发布事件
                EventBus.getDefault().post(new MessageEvent("张三", "123456"));
                break;
        }
    }
}
