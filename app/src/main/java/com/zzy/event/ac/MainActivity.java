package com.zzy.event.ac;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zzy.event.MessageEvent;
import com.zzy.event.MessageEventSticky;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册EventBus
        EventBus.getDefault().register(this);
        Log.d(TAG, Thread.currentThread().getName());
        textView = findViewById(R.id.textView);
    }

    @Override
    protected void onDestroy() {
        // 解除注册EventBus
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // 订阅者与发布者处于相同线程（默认）。此处做耗时操作，会影响后面的订阅者接收消息。
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostiong(MessageEvent messageEvent) {
        Log.d(TAG,  "onMessageEventPostiong " + messageEvent.getName() + "-" + messageEvent.getPsw() + " " + Thread.currentThread().getName());
    }

    // 订阅者在UI线程处理消息。此处做耗时操作，会ANR异常
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMain(MessageEvent messageEvent) {
        textView.setText(messageEvent.getName());
        Log.d(TAG,  "onMessageEventMain " + messageEvent.getName() + "-" + messageEvent.getPsw() + " " + Thread.currentThread().getName());
    }

    // 订阅者会在后台线程调用。
    // 如果发布线程不是UI线程，事件处理会直接在发布线程中调用。此时做耗时操作，会影响后面订阅者接收消息。
    // 如果发布线程是UI线程，事件处理会在后再线程中调用。此时可以做耗时操作
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventBackground(MessageEvent messageEvent) {
        Log.d(TAG,  "onMessageEventBackground " + messageEvent.getName() + "-" + messageEvent.getPsw() + " " + Thread.currentThread().getName());
    }

    // 订阅者会在一个独立的线程中被调用，这个线程独立于UI线程和发布线程。
    // 支持耗时操作，此处用线程池控制。
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEventAsync(MessageEvent messageEvent) {
        Log.d(TAG,  "onMessageEventAsync " + messageEvent.getName() + "-" + messageEvent.getPsw() + " " + Thread.currentThread().getName());
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                EventBus.getDefault().postSticky(new MessageEventSticky("李四", "123456789"));
                Intent intent = new Intent(this, Main2Activity.class);
                this.startActivity(intent);
                break;
        }
    }
}
