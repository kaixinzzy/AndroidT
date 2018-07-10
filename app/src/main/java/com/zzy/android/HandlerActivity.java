package com.zzy.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.zzy.event.ac.R;

import java.lang.ref.WeakReference;

/**
 * Handler运行在主线程，不可以处理耗时操作。
 * Handler容易造成内存泄漏，解决方法如下：
 * 解决方案1：使用静态内部类 + 弱引用（非静态内部类都会拿有外部类的引用）
 * 解决方案2：当外部类结束生命周期时，清空消息队列
 */
public class HandlerActivity extends AppCompatActivity {

    /****************************** Handler start ******************************/
    private final MyHandler myHandler = new MyHandler(this);
    private static class MyHandler extends Handler {
        //定义弱引用
        private final WeakReference<HandlerActivity> wr;

        private MyHandler(HandlerActivity handlerActivity){
            wr = new WeakReference<>(handlerActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HandlerActivity activity = wr.get();
            if (null != activity){
                //处理逻辑
                switch (msg.what){
                    case 0:

                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(10 * 1000);//子线程睡眠10秒
                    myHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    myHandler.sendEmptyMessageDelayed(1,10*1000);
                }

            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空消息队列
        myHandler.removeCallbacksAndMessages(null);
    }
}
