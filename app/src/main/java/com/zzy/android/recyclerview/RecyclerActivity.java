package com.zzy.android.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zzy.event.ac.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private List<RecyclerBean> mListS = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        initData();// 初始化测试数据

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this, mListS);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        if (mListS.size() > 0) {
            return;
        }
        for (int i = 0; i < 100; i++) {
            RecyclerBean bean = new RecyclerBean();
            bean.setName("Name" + i);
            bean.setAge(i);
            mListS.add(bean);
        }
    }


}
