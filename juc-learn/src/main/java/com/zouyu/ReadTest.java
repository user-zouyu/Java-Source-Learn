package com.zouyu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ZouYu 2022/10/10 23:26
 * @version 1.0.0
 */
public class ReadTest {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private static final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private static final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    private volatile int num;

    @RepeatedTest(1)
    @DisplayName("ReadWriteLockTest")
    @SuppressWarnings("all")
    public void test1() throws InterruptedException {
        Thread [] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    readLock.lock();
                    try {
                        System.out.println("read " + finalI + "-" + j + " [" + System.currentTimeMillis() + "] num: " + num);
                    } finally {
                        readLock.unlock();
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {

                    }

                }
            });
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            writeLock.lock();
            try {
                num++;
                System.out.println("write [" + System.currentTimeMillis() + "] num: " + num);
            } finally {
                writeLock.unlock();
            }

            Thread.sleep(10);
        }

    }

}
