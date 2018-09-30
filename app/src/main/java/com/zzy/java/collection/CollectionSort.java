package com.zzy.java.collection;

import com.zzy.java.bean.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 集合排序
 */
public class CollectionSort {

    public static void main(String args[]) {
        CollectionSort cs = new CollectionSort();
        cs.ArrayListSort();
        cs.BeanAttributeSort();
    }

    /**
     * ArrayList<User>() 根据User属性排序
     */
    public void BeanAttributeSort(){
        List<User> listUser = new ArrayList<User>();
        listUser.add(new User("张三", 3));
        listUser.add(new User("王五", 5));
        listUser.add(new User("李四", 4));
        Collections.sort(listUser, new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                Integer lhsInteger = lhs.getAge();
                Integer rhsInteger = rhs.getAge();
                return lhsInteger.compareTo(rhsInteger);
            }
        });
        for (int i = 0; i < listUser.size(); i++) {
            System.out.println(listUser.get(i).getName());//输出:张三 李四 王五
        }
    }

    /**
     * ArrayList排序
     */
    public void ArrayListSort(){
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("c");
        list.add("b");
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));//输出a b c
        }
    }

}
