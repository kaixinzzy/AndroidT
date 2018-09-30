package com.zzy.java.static_;

/**
 * Created by BCLZzy on 2017/9/26.
 *
 * Java静态变量初始化遵循以下规则:
 *      1、只有主动请求一个类,这个类才会初始化,仅包含静态变量,函数,等静态的东西.
 *      2、继承关系时,先初始化父类,后初始化子类.
 *      3、静态变量会按照声明的顺序先依次声明并设置为该类型的默认值,但不赋值为初始化的值.
 *      4、声明完毕后,再按声明的顺序依次设置为初始化的值,如果没有初始化的值就跳过.
 *
 *
 * 参考连接：http://www.jb51.net/article/86629.htm
 */

public class Test {

    public static void main(String args[]) {

        //当类没有被调用，类中的静态变量等是不会被初始化的

        
        /*System.out.println(A.PATH);
        System.out.println(A.BAK_PATH);*/
    }


}
