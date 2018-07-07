package com.zzy.retrofit.api;

import com.zzy.retrofit.module.User;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by BCLZzy on 2018/6/13.
 */

public interface IApiPost {

    @POST("add") // 提交对象
    Call<List<User>> addUser(@Body User user);

    @POST("login") // 单参
    @FormUrlEncoded
    Call<User> loginFormUrl(@Field("userId") String username);

    @POST("login") // 多参
    @FormUrlEncoded
    Call<User> loginFormUrl(@Field("userId") String username, @Field("password") String password);

    @POST("login") // Map
    @FormUrlEncoded
    Call<User> loginFormUrl(@FieldMap HashMap<String, String> paramsMap);

    @POST("login")
    @Multipart // 请求体分为多部分
    Call<User> loginMultiPart(@Part("userId") String userId, @Part("password") String password);

    @POST("login")
    @Multipart // 请求体分为多部分
    Call<User> loginMultiPart(@PartMap HashMap<String, String> paramsMap);
}
