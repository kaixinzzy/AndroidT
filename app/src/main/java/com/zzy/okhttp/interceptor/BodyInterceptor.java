package com.zzy.okhttp.interceptor;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 拦截器，为所有请求参数加密
 * 假设后台给的规则如下：
 *      请求参数名统一为content
 *      content值：Json格式的字符串见过AES加密后的内容，即Json{"mobile":"157xxxxxxxx", "smsCode":"xxxxxx"}通过AES加密
 * 使用：
 *      new OkHttpClient.Builder()
 *          .addNetworkInterceptor(new BodyInterceptor())
 *          .build();
 */
public class BodyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        // AES加密用key
        String keyAES = "11111111111111111111111111111111";

        Request request = chain.request();
        RequestBody body = request.body();
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            Map<String, String> formMap = new HashMap<>();
            // 从formBody中拿到请求参数，放入formMap中
            for (int i = 0; i < formBody.size(); i++) {
                formMap.put(formBody.name(i), formBody.value(i));
            }
            // 将formMap转化成Json然后AES加密
            String jsonParams = new Gson().toJson(formMap);
            byte[] bytesDataAES = ConvertUtils.hexString2Bytes(jsonParams);
            byte[] bytesKeyAES  = ConvertUtils.hexString2Bytes(keyAES);
            // 加密
            byte[] bytesEncryptParams  = EncryptUtils.encryptAES(bytesDataAES, bytesKeyAES, "AES/ECB/NoPadding", null);
            String encryptParams = ConvertUtils.bytes2HexString(bytesEncryptParams);
            // 重新修改body内容
            body = new FormBody.Builder().add("content", encryptParams).build();
        }
        if (body != null) {
            request = request.newBuilder()
                    .post(body)
                    .build();
        }

        return chain.proceed(request);
    }

}
