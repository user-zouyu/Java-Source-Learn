package com.zouyu.lock.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZouYu 2022/9/6 11:50
 * @version 1.0.0
 */
@SuppressWarnings("all")
public class LockSupportTest {

    @Test
    void test() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("线程： " + Thread.currentThread().getName() + " 开始");
            LockSupport.park();
            System.out.println("线程： " + Thread.currentThread().getName() + " 结束");
        }, "lock support test");

        thread.start();

        Thread.sleep(3000);
        System.out.println("unpark");
//        thread.interrupt();
//        LockSupport.unpark(thread);

        thread.join();
    }

    public synchronized void sync() {

    }

    private final ReentrantLock lock = new ReentrantLock(true);
    public void syncLock() {
        lock.lock();
        try {
            // 同步代码
        } catch (Exception ignore) {

        } finally {
            lock.unlock();
        }
    }

    private AtomicInteger count = new AtomicInteger();

    public int incr() {
        return count.incrementAndGet();
    }

}
