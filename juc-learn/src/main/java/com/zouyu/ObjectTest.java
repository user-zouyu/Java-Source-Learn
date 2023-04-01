package com.zouyu;

import org.junit.jupiter.api.Test;

/**
 * @author ZouYu 2022/10/9 16:14
 * @version 1.0.0
 */
public class ObjectTest {
    public static volatile Object lock = new Object();

    @Test
    @SuppressWarnings("all")
    void test1() throws InterruptedException {

        Thread thread = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            synchronized (lock) {
                lock.notify();
            }
            System.out.println("通知线程");
        });

        thread.start();

        System.out.println("进入休眠： wait");
        synchronized (lock) {
            lock.wait();
        }
        System.out.println("退出休眠");
        thread.join();
    }

}
