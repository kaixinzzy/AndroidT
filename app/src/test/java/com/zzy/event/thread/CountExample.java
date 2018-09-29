package com.zzy.event.thread;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 并发计数问题
 */
public class CountExample {

    private int threadTotal = 200;// 线程数
    private int clientTotal = 5000;// 请求总数

    private long count = 0;// 计数

    @Test
    public void CountExample() {
        ExecutorService exec = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        for (int i = 0; i < clientTotal; i++) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        add();
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        exec.shutdown();
        System.out.println("count is " + count);
    }

    private synchronized void add() {
        count++;
    }

}
