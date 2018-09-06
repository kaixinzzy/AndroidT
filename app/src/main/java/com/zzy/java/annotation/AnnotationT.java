package com.zzy.java.annotation;

/**
 * 注解
 *
 * 参考：
 *      http://www.10tiao.com/html/227/201804/2650242853/1.html
 *      https://www.jianshu.com/p/5c8d183533fb
 *      Android进阶之光 for 刘望舒 （书） 第9章
 */
@Swordsman(name = "kaixin", age = 23)
public class AnnotationT {

    @SwordsmanRuntime(name = "哈哈！我是运行时注解属性")
    public void runtimeT() {}

    @SwordsmanClass(name = "哈哈！我是编译时注解属性")
    public void classT() {}

    /*
    注解分类
    1、标准注解
        1.1 @Override
            对覆盖超类中的方法进行标记，如果被标记的方法并没有实际覆盖超类中的方法，则编译器会发出错误警告
        1.2 @Deprecated
            对不鼓励使用或者已经过时的方法进行标记，当编程人员使用被标记的方法时，将会在编译时显示提示信息
        1.3 @SuppressWarnings
            选择性的取消特定代码段中的警告
        1.4 @SafeVarargs
            JDK 7新增，用来声明使用了可变长度参数的方法，其在与泛型类一起使用时不会出现类型安全问题。
     2、元注解 - 标记自定义注解的使用范围
        2.1 @Target
            可修饰对象的范围
            2.1.1 @Target(ElementType.TYPE) 能修饰类、接口、枚举类
            2.1.2 @Target(ElementType.CONSTRUCTOR) 能修饰构造方法
            2.1.3 @Target(ElementType.METHOD) 能修饰方法
            2.1.4 @Target(ElementType.PARAMETER) 能修饰参数
            2.1.5 @Target(ElementType.FIELD) 能修饰成员变量
            2.1.6 @Target(ElementType.LOCAL_VARIABLE) 能修饰局部变量
            2.1.7 @Target(ElementType.ANNOTATION_TYPE) 能修饰注解
            2.1.8 @Target(ElementType.PACKAGE) 能修饰包
            2.1.9 @Target(ElementType.TYPE_PARAMETER) 类型参数声明
            2.1.10 @Target(ElementType.TYPE_USE) 使用类型
        2.2 @Inherited
            表示注解可以被继承
        2.3 @Documented
            表示这个注解应该被JavaDoc工具记录
        2.4 @Retention
            声明注解的保留策略
            2.4.1 @Retention(RetentionPolicy.SOURCE)
                源码级注解。注解信息只会保留在.java源码中，源码在编译后，注解信息被丢弃，不会保留在.class中
            2.4.2 @Retention(RetentionPolicy.CLASS)
                编译时注解。注解信息会保留在.java源码以及.class中。当运行Java程序时，JVM会丢弃该注解，不会保留在JVM中
                可以通过代理模式，对代码进行其他逻辑操作
            2.4.3 @Retention(RetentionPolicy.RUNTIME)
                运行时注解。当运行Java程序时，JVM也会保留该注解信息，
                可以通过反射获取该注解信息
        2.5 @Repeatable
            JDK 8新增，允许一个注解在同一声明类型（类、属性、方法）上多次使用

     3、定义注解
        3.1 简单定义注解请参考类Swordsman
        3.2 定义编译时注解请参考类SwordsmanClass
        3.3 定义运行时注解请参考类SwordsmanRuntime
     4、注解处理器
        注解只起来标记的作用，注解处理器来实现具体的业务逻辑
        4.1 运行时注解处理器，一般用反射来实现逻辑
            参考测试类AnnotationTest
        4.2 编译注解处理器，一般用代理类（AbstractProcessor）来实现逻辑


     */




}
