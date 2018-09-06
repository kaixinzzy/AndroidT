package com.zzy.java.genericity;

/**
 * 泛型接口
 * @param <T>
 */
public interface Generator<T> {

    public T next();

}

// 类实现泛型接口，但未指定泛型参数类型。则此类也必须带泛型<T>
class OneGenerator<T> implements Generator<T>{

    @Override
    public T next() {
        return null;
    }
}

// 类实现泛型接口，并指定泛型参数类型，则此类不需要带泛型<T>
class TwoGenerator implements Generator<String>{

    @Override
    public String next() {
        return null;
    }
}