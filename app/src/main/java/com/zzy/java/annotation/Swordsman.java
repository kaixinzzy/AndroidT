package com.zzy.java.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 自定义注解， 用@interface标记
 */
@Documented
@Target(ElementType.TYPE)
public @interface Swordsman {

    /*
    定义成员变量
    注解只有成员变量，没有方法。注解的成员变量在注解定义中以“无形参的方法”形式来声明，
    其“方法名”定义了该成员变量的名字，其返回值定义了该成员变量的类型
    使用default关键字为其指定默认值
     */
    String name() default "kaixin";
    int age() default 23;



}
