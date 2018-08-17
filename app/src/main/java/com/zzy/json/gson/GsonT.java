package com.zzy.json.gson;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.zzy.json.module.User;

import java.util.ArrayList;
import java.util.List;

/**
 * https://mp.weixin.qq.com/s/pjxRgKQW6EDdlK4rBbmtZQ
 */
public class GsonT {
    private static final String TAG = "GsonT";

    public GsonT() {
        // 测试数据
        String tJsonArray = "[\"https://github.com/leavesC\",\"https://www.jianshu.com/u/9df45b87cfdf\",\"Java\",\"Kotlin\",\"Git\",\"GitHub\"]";
        String[] tStrings = new String[]{"a", "b", "c"};
        List<String> tStringList = new ArrayList<>();
        tStringList.add("a");
        tStringList.add("b");
        tStringList.add("c");
        User tUser = new User("boy", 23, true);
        String tUserJson = "{\"name\":\"boy\",\"age\":23,\"sex\":true}";

        // 获取Gson对象
        Gson gson = new Gson();// 通过构造函数获取Gson
        Gson gs = new GsonBuilder().create();// 通过GsonBuilder获取，可以进行多项特殊配置
        {
            // 生成Json
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("String", "name");
            jsonObject.addProperty("Number_Integer", 23);
            jsonObject.addProperty("Boolean", true);
            JsonObject jsonElement = new JsonObject();
            jsonElement.addProperty("Char", 'c');
            jsonObject.add("jsonElement", jsonElement);// 嵌套
            Log.d(TAG, jsonObject.toString());
        }
        String[] strings = gson.fromJson(tJsonArray, String[].class); // Json数组 转换为 字符串数组
        List<String> stringList = gson.fromJson(tJsonArray, new TypeToken<List<String>>(){}.getType());// Json数组 转换为 List
        gson.toJson(tStrings, new TypeToken<String[]>() {}.getType());// 字符串数组 转换为 Json数组
        gson.toJson(tStringList, new TypeToken<List<String>>(){}.getType());// List 转换为 Json数组
        gson.toJson(tUser);// Bean 序列化成Json
        User user = gson.fromJson(tUserJson, User.class);// Json 反序列化成 Bean



    }

}
