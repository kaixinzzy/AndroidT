package com.zzy.def.base;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by BCLZzy on 2018/6/21.
 */

public class OkGoUtils {

    public OkGoUtils(Application application) {
        // 如不想设置超时时间、https配置、log打印等，可用下面一行，会用默认的。
//        OkGo.getInstance().init(this);
        // 一下为选择自己设置
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /**
         * OkGo内置的log拦截器
         */
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);
        /**
         * 默认使用的超时时间就是60秒
         */
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        /**
         * 配置Cookie，选择一下其中一种即可。也可以自己实现CookieJar的接口，自己管理cookie
         */
        // 方法一 使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(application.getApplicationContext())));
        // 方法二 使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));
        // 方法三 使用内存保持cookie，app退出后，cookie消失
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
        /**
         * Https配置 HttpsUtils只是框架内部提供的方便管理Https的一个工具类，你也可以自己实现，最好只要给OkHttpClient.Builder传递一个sslSocketFactory就行。
         */
        // 方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        // 方法二：自定义信任规则，校验服务端证书
        //HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        // 方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        // 方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        //builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        // 配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
        //builder.hostnameVerifier(new SafeHostnameVerifier());

        /**
         * 以上是okhttp的配置，retrofit和okgo都可以用
         */
        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        OkGo.getInstance().init(application)                    //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数

    }
}
