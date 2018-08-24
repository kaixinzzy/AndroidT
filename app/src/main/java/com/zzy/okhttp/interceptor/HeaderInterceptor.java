package com.zzy.okhttp.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 过滤器，为所有请求添加请求Header
 * 使用：
 *      new OkHttpClient.Builder()
 *          .addNetworkInterceptor(new HeaderInterceptor())
 *          .build();
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //模拟需要设置的Header的token数据
        String token = "xxx";

        // 获取当前请求Request
        Request originalRequest = chain.request();
        // 修改Request的token值
        Request updateRequest = originalRequest.newBuilder()
                .header("token", token)
                .build();
        // 将更改后的Request返回
        return chain.proceed(updateRequest);
    }

}
