package com.zzy.json.gson;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zzy.json.module.PayResult;
import com.zzy.json.module.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Gson
 *
 * 参考：https://mp.weixin.qq.com/s/pjxRgKQW6EDdlK4rBbmtZQ
 */
public class GsonT {
    private static final String TAG = "GsonT";

    // 获取Gson对象
    Gson gson = new Gson();// 通过构造函数获取Gson
    Gson gs = new GsonBuilder().create();// 通过GsonBuilder获取，可以进行多项特殊配置

    List<String> mListString = new ArrayList<>();
    User mUser = new User("boy", 23, true);
    String mUserJson = "{\"name\":\"boy\",\"age\":23,\"sex\":true}";
    String mJsonArray = "[\"https://github.com/leavesC\",\"https://www.jianshu.com/u/9df45b87cfdf\",\"Java\",\"Kotlin\",\"Git\",\"GitHub\"]";
    String[] mStrings = new String[]{"a", "b", "c"};

    public GsonT() {
        mListString.add("a");
        mListString.add("b");
        mListString.add("c");
    }

    public void toJson() {
        // 原始方法生成Json串
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("String", "name");
        jsonObject.addProperty("Number_Integer", 23);
        jsonObject.addProperty("Boolean", true);
        JsonObject jsonElement = new JsonObject();
        jsonElement.addProperty("Char", 'c');
        jsonObject.add("jsonElement", jsonElement);// 嵌套
        Log.d(TAG, "原始方法生成Json串:" + jsonObject.toString());

        // 字符串数组 转换为 Json数组
        String stringsToJson = gson.toJson(mStrings, new TypeToken<String[]>() {}.getType());
        Log.d(TAG, "stringsToJson:" + stringsToJson);

        // Bean 序列化成Json
        String beanToJson = gson.toJson(mUser);
        Log.d(TAG, "beanToJson:" + beanToJson);

        // List 转换为 Json数组
        String listStringToJson = gson.toJson(mListString, new TypeToken<List<String>>(){}.getType());
        Log.d(TAG, "listStringToJson:" + listStringToJson);
    }

    public void toBean() {
        // Json 反序列化成 Bean
        User JsonToUser = gson.fromJson(mUserJson, User.class);
        Log.d(TAG, "JsonToUser:" + JsonToUser.toString());

        // Json数组 转换为 List
        List<String> JsonToListString = gson.fromJson(mJsonArray, new TypeToken<List<String>>(){}.getType());
        Log.d(TAG, "JsonToListString:" + JsonToListString.toString());

        // Json数组 转换为 字符串数组
        String[] JsonToStrings = gson.fromJson(mJsonArray, String[].class);
        Log.d(TAG, "JsonToStrings:" + JsonToStrings.toString());


        String s = "{\"payType\":\"41\",\"message\":\"支付成功，卡内余额：2960.35元\",\"status\":\"SUCCESS\"}";
        PayResult payResult = gson.fromJson(s, PayResult.class);
        Log.d(TAG, "pay json " + s);
        Log.d(TAG, "pay Type " + payResult.getPayType());
        Log.d(TAG, "pay Message " + payResult.getMessage());
        Log.d(TAG, "pay Status " + payResult.getStatus());
    }

}
