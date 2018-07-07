package com.zzy.okhttp.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.zzy.event.ac.R;
import com.zzy.okhttp.ReqCallBack;
import com.zzy.okhttp.RequestManager;
import com.zzy.okhttp.module.wan.ArticleList;

import java.util.HashMap;
import java.util.List;

public class OkhttpActivity extends AppCompatActivity implements ReqCallBack {
    private static final String TAG = "OkhttpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp2);

        HashMap<String, String> map = new HashMap<>();
        map.put("offset", "0");// 起始位置
        map.put("limit", "20");// 每次获取数据的条数
        map.put("client_sys", "android");
        // 同步 get
//        RequestManager.getInstance(this).requestSyn("login", TYPE_GET,  map);
        // 同步 post json
//        RequestManager.getInstance(this).requestSyn("login", TYPE_POST_JSON,  map);
        // 同步 post 表单
//        RequestManager.getInstance(this).requestSyn("login", TYPE_POST_FORM,  map);

        // 异步 get
        RequestManager.getInstance(this).requestGetByAsyn("article/list/%s/json", "0", this);
        // 异步 post json
//        RequestManager.getInstance(this).requestAsyn("", TYPE_POST_JSON, map, this);
        // 异步 post 表单
//        RequestManager.getInstance(this).requestAsyn("", TYPE_POST_FORM, map, this);
    }

    @Override
    public void onReqSuccess(Object result) {
        Log.d(TAG, "onReqSuccess " + result);
        ArticleList wanArticleList = new Gson().fromJson(String.valueOf(result), ArticleList.class);
        if (wanArticleList != null) {
            ArticleList.DataBean dataBean = wanArticleList.getData();
            if (dataBean != null) {
                List<ArticleList.DataBean.DatasBean> list = dataBean.getDatas();
                for (ArticleList.DataBean.DatasBean datasBean : list) {
                    Log.d(TAG,  datasBean.getTitle() + "\n作者：" + datasBean.getAuthor() + "\n地址:" + datasBean.getLink());
                }
            }
        }
    }

    @Override
    public void onReqFailed(String errorMsg) {
        Log.d(TAG, "onReqFailed " + errorMsg);
    }
}
