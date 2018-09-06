package com.zzy.json.def;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Android原始解析Json
 * https://blog.csdn.net/lilu_leo/article/details/7000077
 */
public class JsonT {
    private static final String TAG = "JsonT";

    public void toJson() {
        // 假设现在要创建这样一个json文本
        //  {
        //      "name" : "yuanzhifei89", // 字符串
        //      "age" : 100, // 数值
        //      "married" : false // 布尔值
        //      "phone" : ["12345678", "87654321"], // 数组
        //      "address" : { "country" : "china", "province" : "jiangsu" }, // 对象
        //  }
        try {
            //JSONObject用{}包裹
            JSONObject person = new JSONObject();

            person.put("name", "kaixin");
            person.put("age", 18);
            person.put("marred", false);

            //JSONArray用[]包裹
            JSONArray phone = new JSONArray();
            phone.put("12345678").put("87654321");
            person.put("phone", phone);

            JSONObject address = new JSONObject();
            address.put("country", "china");
            address.put("province", "jiangsu");
            person.put("address", address);

            Log.d(TAG, "toJson " + person.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void toBean() {
        //  {
        //      "phone" : ["12345678", "87654321"], // 数组
        //      "name" : "yuanzhifei89", // 字符串
        //      "age" : 100, // 数值
        //      "address" : { "country" : "china", "province" : "jiangsu" }, // 对象
        //      "married" : false // 布尔值
        //  }
        String json =
                "{" +
                    "   \"phone\" : [\"12345678\", \"87654321\"]," +
                    "   \"name\" : \"kaixin\"," +
                    "   \"age\" : 100," +
                    "   \"address\" : { \"country\" : \"china\", \"province\" : \"jiangsu\" }," +
                    "   \"married\" : false" +
                "}";
        try {
            JSONTokener jsonParser = new JSONTokener(json);
            JSONObject person = (JSONObject) jsonParser.nextValue();
            JSONArray phone = person.getJSONArray("phone");
            for (int i = 0; i < phone.length(); i++) {
                Log.d(TAG, "toBean " + phone.get(i));
            }
            Log.d(TAG, "toBean " + person.getString("name"));
            Log.d(TAG, "toBean " + person.getInt("age"));
            Log.d(TAG, "toBean " + person.getBoolean("married"));
            JSONObject address =  person.getJSONObject("address");
            Log.d(TAG, "toBean " + address.getString("country"));
            Log.d(TAG, "toBean " + address.getString("province"));



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
