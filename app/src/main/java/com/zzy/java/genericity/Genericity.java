package com.zzy.java.genericity;

/**
 * 泛型T所代表的参数类型，只能是引用类型，不能是基本数据类型。即可以是Integer，不可以是int
 *
 * @param <T> 泛型标识，常用的有<T><E><K><V>
 * 不能对具体的泛型类型使用instanceof操作：如
 *           Genericity<String> genericity = new Genericity<String>("aaa");
 *           if( genericity instanceof Genericity<String>)这里会报错
 */
public class Genericity<T> {
    private T key;

    public Genericity(T key) {
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    /**
     * 泛型方法
     * public与返回值中间有<T>、<T,K>、<T,K,V>，代表此方法为泛型方法。
     * 其中T、K、V等泛型类型可以出现在此方法中的任意位置，T、K、V可以出现任意多个。
     * @param <T>
     * @return
     */
    public <T,K> T genericMethod(T t, K k) {

        return t;
    }
}
