package com.zzy.java.collection;

public class CollectionMap {

    /*
     在讲解Map排序之前，我们先来稍微了解下map。map是键值对的集合接口，
     它的实现类主要包括：HashMap,TreeMap,Hashtable以及LinkedHashMap等。其中这四者的区别如下（简单介绍）：

     1、HashMap：
     我们最常用的Map，它根据key的HashCode 值来存储数据,根据key可以直接获取它的Value，
     同时它具有很快的访问速度。HashMap最多只允许一条记录的key值为Null(多条会覆盖);允许多条记录的Value为 Null。非同步的。

     2、TreeMap:
     能够把它保存的记录根据key排序,默认是按升序排序，也可以指定排序的比较器，
     当用Iterator 遍历TreeMap时，得到的记录是排过序的。TreeMap不允许key的值为null。非同步的。

     3、Hashtable:
     与 HashMap类似,不同的是:key和value的值均不允许为null;它支持线程的同步，
     即任一时刻只有一个线程能写Hashtable,因此也导致了Hashtale在写入时会比较慢。

     4、LinkedHashMap:
     保存了记录的插入顺序，在用Iterator遍历LinkedHashMap时，先得到的记录肯定是先插入的.
     在遍历的时候会比HashMap慢。key和value均允许为空，非同步的。
     */

}
