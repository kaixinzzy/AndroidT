package com.zzy.java.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by BCLZzy on 2017/11/2.
 * HashMap
 *
 * 优缺点：
 *      TreeMap是SortedMap接口基于红黑树的实现，该类保证了映射按照升序排列关键字。【有序排列 默认升序排列】
 *      HashMap是根据键的HashCode 值存储数据，取得数据的顺序是完全随机的，【无序排列】【HashMap取值的速度更快】。
 * Map的特点：
 *      map中key是唯一的，如果再次插入已有的键值对，那么会覆盖以存在key的value值.
 *      当map.get(key)时，map中没有相应的key时，获取到的值是null，不会报异常.
 */
public class CollectionHashMap {

    public static void main(String args[]) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("a", "a");
        hashMap.put("c", "c");
        hashMap.put("b", "b");

        CollectionHashMap collectionHashMap = new CollectionHashMap();
        collectionHashMap.SortWithKey(hashMap);
        collectionHashMap.SortWithValue(hashMap);
    }

    /**
     * HashMap根据value排序
     *      由于HashMap不支持有序的存储方式，所以排序完成后，只能存放到list中。
     */
    private void SortWithValue(HashMap<String, String> hashMap) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("c", "ccccc");
        map.put("a", "aaaaa");
        map.put("b", "bbbbb");
        map.put("d", "ddddd");

        List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,String>>() {
            //升序排序
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                //如果要降序，只需要将o1和o2调换位置即可
                return o1.getValue().compareTo(o2.getValue());
            }

        });

        for(Map.Entry<String,String> mapping:list){
            System.out.println(mapping.getKey()+":"+mapping.getValue());
        }
    }

    /**
     * HashMap根据key排序
     * 原理：
     *      TreeMap默认支持key排序，我们用HashMap来构造一个TreeMap即可
     *      由于HashMap不支持有序的存储方式，所以排序完成后，只能存放到TreeMap中。
     */
    private void SortWithKey(HashMap<String, String> hashMap) {
        new TreeMap<String, String>(hashMap);
    }

}
