package com.zouyu.lock.atomic;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZouYu 2022/9/4 22:50
 * @version 1.0.0
 */


public class AtomicTest {

    public static class SyncCount {
        private Integer count = 0;

        public Integer getCount() {
            return count;
        }

        public synchronized void incr() {
            count++;
        }
    }

    @Test
    void syncCountTest() throws InterruptedException {
        SyncCount syncCount = new SyncCount();

        long currentTimeMillis = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10000000; i++) {
                syncCount.incr();
            }
        });

        thread.start();

        for (int i = 0; i < 10000000; i++) {
            syncCount.incr();
        }
        thread.join();

        System.out.println("Time: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
        System.out.println(syncCount.getCount());
    }


    public static class AtomicCount {
        private final AtomicInteger count = new AtomicInteger();

        public Integer getCount() {
            return count.get();
        }

        public void incr() {
            count.incrementAndGet();
        }
    }

    @Test
    void atomicCountTest() throws InterruptedException {
        AtomicCount atomicCount = new AtomicCount();

        long currentTimeMillis = System.currentTimeMillis();

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10000000; i++) {
                atomicCount.incr();
            }
        });

        thread.start();

        for (int i = 0; i < 10000000; i++) {
            atomicCount.incr();
        }

        thread.join();

        System.out.println("Time: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
        System.out.println(atomicCount.getCount());
    }

    @Test
    void test() {
        Integer count = 0;
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < 20000000; i++) {
            count++;
        }

        System.out.println("Time: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");

        System.out.println(count);
    }

}
