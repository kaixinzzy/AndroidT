package com.zzy.libraryrx2retrofit2.api;

import com.zzy.libraryrx2retrofit2.api.bean.ArticleData;
import com.zzy.libraryrx2retrofit2.api.bean.HttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @GET("/student/login")
    Observable<Object> login(@Query("phone") String phone, @Query("password") String psw);

    @GET("article/list/{page}/json")
    Observable<HttpResult<ArticleData>> getArticleList(@Path("page") int page);

}
