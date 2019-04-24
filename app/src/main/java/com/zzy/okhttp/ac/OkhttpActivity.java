package com.zzy.okhttp.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.zzy.event.ac.R;
import com.zzy.okhttp.ReqCallBack;
import com.zzy.okhttp.RequestManager;
import com.zzy.okhttp.module.wan.ArticleList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    /** Okhttp get 同步请求 **/
    private void getExecute() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("www.jd.com")
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            Log.d(TAG, response.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /** Okhttp get 异步请求 **/
    private void getEnqueue() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("www.jd.com")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.body().string());
            }
        });
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /** Okhttp post 同步请求 **/
    private void postExecute() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, "xxx");
        Request request = new Request.Builder()
                .url("www.jd.com")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            Log.d(TAG, response.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Okhttp post 异步请求 **/
    private void postEnqueue() {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, "xxx");
        Request request = new Request.Builder()
                .url("www.jd.com")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.body().string());
            }
        });
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
