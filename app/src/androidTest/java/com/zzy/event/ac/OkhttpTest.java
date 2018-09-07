package com.zzy.event.ac;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Okhttp测试类
 */
@RunWith(AndroidJUnit4.class)
public class OkhttpTest {
    public static final String TAG = "OkhttpTest";
    public String mUrl = "http://blog.csdn.net/itachi85";
    public String mUrlIp = "http://ip.taobao.com/service/getIpInfo.php";

    public Call init(String url, RequestBody requestBody) {
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (null == requestBody) {
            // GET请求
            requestBuilder.method("GET", null);
        } else {
            // POST请求
            requestBuilder.post(requestBody);
        }

        Request request = requestBuilder.build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        return call;
    }

    public void enqueue(Call call) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure" + call.toString());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.d(TAG, "onResponse " + str);
            }
        });
    }

    public void execute(Call call) {
        try {
            Response response = call.execute();
            if (response.body() != null) {
                String s = response.body().string();
                Log.d(TAG, s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test // GET 异步
    public void getEnqueue() {
        Call call = init(mUrl, null);
        enqueue(call);
    }

    @Test // GET 同步
    public void getExecute() {
        Call call = init(mUrl, null);
        execute(call);
    }

    @Test // POST 异步
    public void postEnqueue() {
        FormBody formBody = new FormBody.Builder()
                .add("ip", "59.108.54.37")
                .build();
        Call call = init(mUrlIp, formBody);
        enqueue(call);
    }

    @Test // POST 同步
    public void postExecute() {
        FormBody formBody = new FormBody.Builder()
                .add("ip", "59.108.54.37")
                .build();
        Call call = init(mUrlIp, formBody);
        execute(call);
        int i = 100;
        for (int i1 = 0; i1 < i; i1++) {
            Log.d(TAG, "condition = " + i1);
        }
    }


}
