package com.zouyu.lock.lock;

import com.zouyu.common.TimeUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ZouYu 2023/4/9 17:13
 * @version 1.0.0
 */
public class ReentrantReadWriteLockTest {

    @Test
    void test1() throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();


        new Thread(() -> {
            readLock.lock();
            try {
                System.out.println("Hello");
            } finally {
                readLock.unlock();
            }
        }).start();
        TimeUtils.SECONDS.sleep(2);

        readLock.lock();
        try {
            System.out.println("Hello");
        } finally {
//            writeLock.unlock();
        }

    }
}
