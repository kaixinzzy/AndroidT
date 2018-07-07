package com.zzy.retrofit.api;

import com.zzy.retrofit.module.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by BCLZzy on 2018/6/13.
 */

public interface IApiGet {

    // Header --------------------------------------------------------------------------------------

    // 静态header，如果这里设置了header，下面就不用设置了。而且这样静态设置，header是不可变的。
    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })

    //@Headers("apikey:9c32fd04cda3a80c3cff414c7e50e2f5")// 静态请求头
    @GET
    Call<List<User>> getHeaderStatic();

    @GET("users") // 动态请求头 单参
    Call<List<User>> getHeader(@Header("apikey") String apikey);

    @GET("users") // 动态请求头 多参
    Call<List<User>> getHeader(@Header("apikey") String apikey, @Header("Accept") String accept);

    @GET("users") // 动态请求头 Map
    Call<List<User>> getHeaderMap(@HeaderMap Map<String, String> apikeys);

    // 直接请求，不带参数 ----------------------------------------------------------------------------

    @GET("users") // 静态地址
    Call<List<User>> get();

    @GET          // 动态地址
    Call<List<User>> get(@Url String url);

    // Path 动态路径 --------------------------------------------------------------------------------

    @GET("users/{id}/id") // 动态路径
    Call<List<User>> getUsers(@Path("id") String userId);

    // GET -----------------------------------------------------------------------------------------

    @GET("users") // 单参
    Call<List<User>> getQuery(@Query("name") String name);

    @GET("users") // 多参
    Call<List<User>> getQuerys(@Query("name") String name, @Query("age") int age);

    @GET("users") // Map（形如：?id=xxx&name=xxx）
    Call<List<User>> getQueryMap(@QueryMap HashMap<String, String> paramsMap);



}
