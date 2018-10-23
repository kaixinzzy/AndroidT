package com.zzy.java.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 */
public class ThreadPool {

    public void FixedThreadPool() {
        // 创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.execute(new MyThread());
        pool.execute(new MyThread());
        pool.execute(new MyThread());
        // 关闭线程池
//        pool.shutdown();
    }

    public void SingleThreadExecutor() {
        // 单任务线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(new MyThread());
        pool.execute(new MyThread());
        pool.execute(new MyThread());
    }

    public void CachedThreadPool() {
        // 可变尺寸的线程池
        // 线程数无限大，当一个线程执行完成，正好新添加一个线程时，会重用线程，
        // 有线程闲置超过60秒，会自动被回收
        // 如果无可用线程，会新建线程
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(new MyThread());
        pool.execute(new MyThread());
        pool.execute(new MyThread());
    }

    public void ScheduledThreadPool() {
        // 创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
        pool.execute(new MyThread());
        // 延迟1秒执行
        pool.schedule(new MyThread(), 1000, TimeUnit.MILLISECONDS);
        // 延迟1秒后每隔3秒执行一次
        pool.scheduleAtFixedRate(new MyThread(), 1, 3, TimeUnit.SECONDS);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            Thread.currentThread().getName();
        }
    }

}
