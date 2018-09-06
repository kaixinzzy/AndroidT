package com.zzy.event.ac;

import com.zzy.java.annotation.AnnotationT;
import com.zzy.java.annotation.SwordsmanRuntime;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 注解相关
 */
public class AnnotationTest {

    @Test
    public void runtimeT() {
        // 通过反射获取类中所有方法
        Method[] methods = AnnotationT.class.getDeclaredMethods();
        // 遍历所有方法
        for (Method method : methods) {
            // 取得标记用SwordsmanRuntime注解的方法
            SwordsmanRuntime swordsmanRuntime = method.getAnnotation(SwordsmanRuntime.class);
            if (swordsmanRuntime != null) {
                // 打印注解的属性值
                System.out.println(swordsmanRuntime.name());
            }

        }
    }

}
