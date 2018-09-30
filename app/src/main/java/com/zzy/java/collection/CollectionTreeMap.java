package com.zzy.java.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by BCLZzy on 2017/11/2.
 * TreeMap 有排序的Map
 *      默认是从小到大排序
 *      要想从大到小排序，创建TreeMap的时候要要传入自定义的比较器
 * 优缺点：
 *      TreeMap是SortedMap接口基于红黑树的实现，该类保证了映射按照升序排列关键字。【有序排列 默认升序排列】
 *      HashMap是根据键的HashCode 值存储数据，取得数据的顺序是完全随机的，【无序排列】【HashMap取值的速度更快】。
 * Map的特点：
 *      map中key是唯一的，如果再次插入已有的键值对，那么会覆盖以存在key的value值.
 *      当map.get(key)时，map中没有相应的key时，获取到的值是null，不会报异常.
 */
public class CollectionTreeMap {

    public static void main(String args[]) {
        CollectionTreeMap collectionTreeMap = new CollectionTreeMap();
        collectionTreeMap.SortWithKey();
        collectionTreeMap.SortWithValue();

    }

    /**
     * TreeMap根据key排序
     *      默认是根据key升序排列的
     */
    private void SortWithKey() {
        //这样是升序排列
        //Map<String, String> map = new TreeMap<String, String>();
        //这样是降序排列
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
                });
        map.put("c", "ccccc");
        map.put("a", "aaaaa");
        map.put("b", "bbbbb");
        map.put("d", "ddddd");

        println(map);
    }

    /**
     * TreeMap根据Value排序
     *      由于TreeMap不支持按Value的排序的存储方式，所以排序完成后，只能存放到list中。
     */
    private void SortWithValue() {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("d", "ddddd");
        map.put("b", "bbbbb");
        map.put("a", "aaaaa");
        map.put("c", "ccccc");

        //这里将map.entrySet()转换成list
        List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
        //然后通过比较器来实现排序
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
     * 遍历map  并打印
     */
    private void println(Map<String, String> map){
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + map.get(key));
        }
    }

}
