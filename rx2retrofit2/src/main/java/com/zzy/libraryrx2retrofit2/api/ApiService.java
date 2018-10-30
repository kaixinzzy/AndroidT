package com.zzy.libraryrx2retrofit2.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求统一管理
 */
public class ApiService {

    private static ApiService sAPIServer;
    private final String BASE_URL = "http://www.wanandroid.com";
    private API apiObservable;

    private ApiService() {
        // 初始化OkHttp
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)                  // 是否自动重连
                .connectTimeout(15, TimeUnit.SECONDS)   // 设置连接超时
                .readTimeout(45, TimeUnit.SECONDS)      // 设置读取超时
                .writeTimeout(55, TimeUnit.SECONDS)     // 设置写入超时
                .addInterceptor(headerInterceptor)              // 请求头拦截器
//                .addInterceptor(loggingInterceptor)
//                .cookieJar(cookieJar)
                .build();
        // 初始化Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)                                  // 指定服务器主机地址
                .client(client)                                     // 指定OkHttpClient
                .addConverterFactory(GsonConverterFactory.create()) // 添加gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 添加rxjava转换器
                .build();
        apiObservable = retrofit.create(API.class);
    }

    public static ApiService getInstance() {
        // 双重校验锁实现懒汉式单利
        if (sAPIServer == null) {
            synchronized (ApiService.class) {
                if (sAPIServer == null) {
                    sAPIServer = new ApiService();
                }
            }
        }
        return sAPIServer;
    }

    public API API() {
        return apiObservable;
    }

    // 请求头拦截器
    private Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            return response.newBuilder()
                    .header("key1","value1")
                    .header("key2","value2")
                    .build();
        }
    };

}
