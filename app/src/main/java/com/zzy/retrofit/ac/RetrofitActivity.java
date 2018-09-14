package com.zzy.retrofit.ac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.zzy.event.ac.R;
import com.zzy.retrofit.api.IApiGet;
import com.zzy.retrofit.constant.Constant;
import com.zzy.retrofit.module.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
    public static final String TAG = "RetrofitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
        // init okhttp3 logger
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        // init cache
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)                  // 是否自动重连
                .connectTimeout(15, TimeUnit.SECONDS)   // 设置连接超时
                .readTimeout(45, TimeUnit.SECONDS)      // 设置读取超时
                .writeTimeout(55, TimeUnit.SECONDS)     // 设置写入超时
//                .addInterceptor(loggingInterceptor)
//                .cookieJar(cookieJar)
                .build();

        // 创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)                     // 指定服务器主机地址
                .client(client)                                 // 指定OkHttpClient
                // 指定解析方式，此处使用Gson解析，请求后直接返回实体对象 需要引入compile 'com.squareup.retrofit2:converter-gson:2.3.0'
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IApiGet iApiGet = retrofit.create(IApiGet.class);

        // Call对象表示一个已经准备好可以执行的请求，用这个对象可以查询请求的执行状态，或者取消当前请求。
        Call<List<User>> call = iApiGet.getQuerys("10", Constant.PAGE_SIZE);
        // 异步请求数据
        call.enqueue(new Callback<List<User>>() {
            @Override // 请求成功
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response != null) {
                    Log.d(TAG, response.body().size() + "");
                } else {
                    Log.d(TAG, "response is null");
                }

            }

            @Override // 请求失败
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
        // 同步执行网络请求，不要在主线程执行
        Response<List<User>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        call.cancel();      // 请求取消
        call.cancel();      // 查询请求是否取消
        call.isExecuted();  // 查询请求是否被执行过
        call.clone();       // 每个Call只可以执行一次请求，如果需要执行多次，可以使用clone方法，回去复制一个相同的Call对象

        // Response是网络请求的结果
        response.code();                        //请求的状态码
        response.isSuccessful();                //如果状态码为[200..300)，则表明请求成功
        Headers headers=response.headers();     //获取响应头
        String names=headers.get("name");       //获取响应头中的某个字段
        List<User> body = response.body();      //获取响应体

    }
}
