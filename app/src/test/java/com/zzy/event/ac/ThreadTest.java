package com.zzy.event.ac;

import com.zzy.java.thread.Node;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 图解Java多线程设计模式-结城浩
 * 练习题
 */
public class ThreadTest {

    // 基础知识测试
    public void test1_1() {
        /*
        在Java程序中，至少有一个线程在运行          1
        调用Thread类的run方法后，新的线程就会启动   0
        start方法和run方法声明在Runnable接口中     1
        有时多个线程会调用同一个实例的方法           1
        sleep方法执行后，在指定时间内所有的线程都会暂停 0
        某个线程在运行synchronized方法时，其他所有线程都会停止运行 0
        执行sleep方法后的线程仅在指定时间内待在等待队列 1
        wait方法的调用语句必须写在synchronized方法中
        notifyAll方法时java.lang.Object类的实例方法 1
         */

        // 1.2 因为程序是单线程的
        // 1.3 因为调用的是run方法，而不是start方法，所以程序时单线程的，会顺序执行

        /*
        一般推荐用实现Runnable接口的方式实现线程，因为，一个雷应该在其需要加强或者修改时才会被继承。

        线程终止条件：
            1、run方法执行完毕
            2、发生异常


         竞争条件：多个线程同时要购买同一坐席的火车票，并且都会修改这张火车票在数据库中的记录值。此时为竞争条件。可以用锁来解决。
         原子性：唯一性，火车票是唯一的，不可能产生相同坐席的火车票

         // 《码农翻身》第二章 加锁还是不加锁，这是一个问题中讲的比较清楚。
         synchronized 悲观锁，对于阻塞而言，激活是一笔不小的开销
         CAS
         ABA问题
         */
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        AtomicLong atomicLong = new AtomicLong(1);
//        AtomicLongArray atomicLongArray = new AtomicLongArray();
//        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray();

        AtomicReference<Node> headAR = new AtomicReference<>();
//        AtomicStampedReference<Node> headASR = new AtomicStampedReference<>();
        while (true) {
            // 记录原来的head
            Node oldHead = headAR.get();
            if (oldHead == null) {
                break;
            }
            // 记录新的head
            Node newHead = oldHead.next;

            if (headAR.compareAndSet(oldHead, newHead)) {
                oldHead.next = null;
//                return oldHead.data;
            }
        }

    }


}
