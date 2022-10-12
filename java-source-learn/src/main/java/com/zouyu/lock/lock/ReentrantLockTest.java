package com.zouyu.lock.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZouYu 2022/10/4 14:14
 * @version 1.0.0
 */
public class ReentrantLockTest {

    private final ReentrantLock lock = new ReentrantLock(true);


    @Test
    void test() throws InterruptedException {
//        lock.lock();
        Condition condition = lock.newCondition();
        condition.await();
        try {
            System.out.println("lock code");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            condition.signal();
//            lock.unlock();
        }
    }


    @Test
    void parkTest() throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(200);
                    System.out.println("i = " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        LockSupport.parkNanos(1000);

        Thread.sleep(100);
        LockSupport.unpark(thread);
        System.out.println("unpark");
        thread.join();
    }

}
