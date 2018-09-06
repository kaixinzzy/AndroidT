package com.zzy.event.ac;

import com.zzy.java.genericity.Genericity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 运行在本机Java虚拟机上的单元测试
 *
 * 参考：https://www.jianshu.com/p/de17655125f5
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void zzy() {
        System.out.println("Java 测试场");
    }

    /**
     * 泛型
     * 参考：https://blog.csdn.net/s10461/article/details/53941091
     */
    @Test
    public void genericity() {
        // 限制类型写法
        Genericity<Integer> genericityInt = new Genericity<>(123);
        Genericity<String> genericityStr = new Genericity<>("阿丑");
        System.out.println("key is " + genericityInt.getKey());
        System.out.println("key is " + genericityStr.getKey());

        // 不限制参数类型写法
        System.out.println("key is " + new Genericity(123456).getKey());
        System.out.println("key is " + new Genericity("你才丑").getKey());
        System.out.println("key is " + new Genericity(false).getKey());
        System.out.println("key is " + new Genericity(66.66).getKey());

        //
        showGenericityKey(genericityInt);
        showGenericityKey(genericityStr);
    }

    /**
     * 泛型通配符
     * ?代表类型实参，而不是类型形参。
     * ?和String、Integer一样都是实际的类型。可以把?看做所有类型的父类
     * @param obj
     */
    public void showGenericityKey(Genericity<?> obj) {
        System.out.println("obj" + obj.getKey());
    }


    /**
     * 逻辑与，当两边都为true时才返回true
     *  &  叫按位与。既是位运算符又是逻辑运算符，&的两侧可以是int，也可以是boolean表达式。
     *      当&两侧是int时，要先把运算符两侧的数转化为二进制数再进行运算
     *      当前面为false时，会再判断后面，然后才会返回false
     *  && 叫短路与。两侧要求必须是boolean表达式。
     *      当前面为false时，不再判断后面，直接返回false
     * 逻辑或，当一个为true时，就返回true
     *  |  当前面为true时，会再判断后面，然后返回true
     *  || 当前面为true时，不再判断后面，直接返回true
     * 参考：https://blog.csdn.net/sum_tw/article/details/55683186
     */
    @Test
    public void State() {
        int state_none = 0;    // 二进制：00 00 00 00
        int state_error_1 = 1; // 二进制：00 00 00 01
        int state_error_2 = 2; // 二进制：00 00 00 10
        int state_error_3 = 4; // 二进制：00 00 01 00
        int state_error_4 = 8; // 二进制：00 00 10 00
        int state_error_5 = 16;// 二进制：00 01 00 00
        int state_error_6 = 32;// 二进制：00 10 00 00

        int state = state_error_1 + state_error_3; // 二进制：00 00 01 01
        // 计算过程
        // state   00 00 01 01
        // error_1 00 00 00 01
        // result  00 00 00 01
        // 按位一一&，只有当两个值都为1时，结果才会是1，否则为0
        if ((state & state_error_1) != 0) {
            System.out.println("包含了 state_error_1");
        }
        if ((state & state_error_3) != 0) {
            System.out.println("包含了 state_error_3");
        }
        if ((state & state_error_2) == 0) {
            System.out.println("没有包含 state_error_2");
        }

        int i = 0;
        if (3>2 || (i++) > 1) {
            // 后面i++不会执行
        }
        if (3>2 | (i++) > 1) {
            // 后面i++会执行
        }
    }

}