package com.zzy.retrofit.api;

import com.zzy.retrofit.module.CardModel;
import com.zzy.retrofit.module.PostBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by BCLZzy on 2018/6/13.
 */

public interface PhoneApiService {
    // 静态header，如果这里设置了header，下面就不用设置了。而且这样静态设置，header是不可变的。
/*    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })*/

    // 直接调用
    @Headers("apikey:9c32fd04cda3a80c3cff414c7e50e2f5")
    @GET
    Call<CardModel> getFromUrl(@Url String url);

    // 单参数拼接
    @GET("/apistore/idservice/id")
    Call<CardModel> getCardGetState(
            // 请求参数，形如：?id=xxx
            @Query("id") String id);

    // 单参数替换
    @GET("/apistore/{idservice}/id")
    Call<CardModel> getWithPath(
            @Path("idservice") String idservice
    );

    // 单参数
    @GET("/apistore/idservice/id")
    Call<CardModel> getCardGetState(
            // 动态添加请求header
            @Header("apikey") String apikey,
            // 请求参数，形如：?id=xxx
            @Query("id") String id);

    // 多header 多参数
    @GET("/apistore/idservice/id")
    Call<CardModel> getCardGetState(
            @HeaderMap Map<String, String> headerMap,
            @QueryMap Map<String, String> map);

    // 表单参数
    @FormUrlEncoded
    @POST("/app/mediator/login")
    Call<CardModel> loginPost(
            @Field("telephone") String telephone,
            @Field("pwd") String pwd);

    // 对象参数
    @POST("/app/mediator/login")
    Call<CardModel> loginPost(@Body PostBean postBean);
}
