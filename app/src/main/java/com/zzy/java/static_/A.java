package com.zzy.java.static_;

import java.io.File;

/**
 * 静态变量会按照声明的顺序先依次声明并设置为该类型的默认值,但不赋值为初始化的值.
 * 声明完毕后,再按声明的顺序依次设置为初始化的值,如果没有初始化的值就跳过.
 */
public class A {
    public static A a = new A("A ");

    //当前A.PATH的值为类型的默认值，还未赋值为初始化的值，所以是null
    public static final String BAK_PATH = A.PATH + File.separator + "Bak" + File.separator;

    public static final String PATH = B.SDCARD_PATH;

    private A(String a){
        System.out.println(a + B.SDCARD_PATH);
        System.out.println(a + A.PATH);
    }

/*    public static A getA(){
        return a;
    }

    public static void main(String args[]) {
        A.getA();
    }*/




}
