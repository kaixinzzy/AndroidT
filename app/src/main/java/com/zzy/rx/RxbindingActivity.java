package com.zzy.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.zzy.event.ac.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 参考： https://www.cnblogs.com/zhaoyanjun/p/5535651.html
 */
public class RxbindingActivity extends AppCompatActivity {

    @BindView(R.id.rxbinding_button)
    Button mButton;

    @BindView(R.id.rxbinding_listview)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbinding);

        // 点击事件监听
        RxView.clicks(mButton)
                // 两秒钟之内只取一个点击事件，防抖事件
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(RxbindingActivity.this, "按钮点击事件", Toast.LENGTH_LONG).show();
                    }
                });

        // 长按事件监听
        RxView.longClicks(mButton)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(RxbindingActivity.this, "按钮长按事件", Toast.LENGTH_LONG).show();
                    }
                });

        // ListVIew item 点击事件监听
        RxAdapterView.itemClicks(mListView)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Toast.makeText(RxbindingActivity.this, "ListView item 点击事件", Toast.LENGTH_LONG).show();
                    }
                });

        // ListVIew item 长按事件监听
        RxAdapterView.itemLongClicks(mListView)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Toast.makeText(RxbindingActivity.this, "ListView item 长按事件", Toast.LENGTH_LONG).show();
                    }
                });

    }



}
