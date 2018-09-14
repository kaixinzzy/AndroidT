package com.zzy.event.ac;

import org.junit.Test;

import java.util.HashMap;

public class MapTest {

    /**
     * 通过key获取值时，没有对应值情况。
     */
    @Test
    public void getValue() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("showMsg", "显示内容");
        map.put("state", 3);
        map.put("hasDrawable", true);
        Object object = map;

        HashMap<String, Object> mapResult = (HashMap<String, Object>) object;

        Object msgObj = mapResult.get("showMsg");
        String showMsg = (msgObj == null) ? "" : (String) msgObj;

        Object stateObj = mapResult.get("state");
        int state = Integer.parseInt(stateObj == null ? "0" : String.valueOf(stateObj));

        Object drawableObj = mapResult.get("hasDrawable");
        boolean hasDrawable = (drawableObj != null) && Boolean.parseBoolean(String.valueOf(drawableObj));

        System.out.println("数据：" + showMsg);
        System.out.println("数据：" + state);
        System.out.println("数据：" + hasDrawable);
    }

}
