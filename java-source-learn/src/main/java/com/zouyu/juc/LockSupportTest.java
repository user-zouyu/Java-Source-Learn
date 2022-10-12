package com.zouyu.juc;

import org.junit.jupiter.api.*;

import java.util.concurrent.locks.LockSupport;

/**
 * @author ZouYu 2022/10/9 14:52
 * @version 1.0.0
 */
public class LockSupportTest {

    @Test
    @SuppressWarnings("all")
    @DisplayName("先park在unpark")
    void test() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("进入线程 执行 park");
            LockSupport.park();
            System.out.println("退出线程");
        });

        thread.start();
        System.out.println("启动线程");
        Thread.sleep(1000);
        System.out.println("unpark()");
        LockSupport.unpark(thread);
        thread.join();
    }

    @Test
    @DisplayName("先unpark在park")
    @SuppressWarnings("all")
    void test2() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("进入线程");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {}
            System.out.println("执行 park");
            LockSupport.park();
            System.out.println("退出线程");
        });

        System.out.println("启动线程");
        thread.start();

        Thread.sleep(500);
        System.out.println("unpark()");
        LockSupport.unpark(thread);

        thread.join();
    }

    @Test
    @DisplayName("void park(Object blocker)")
    @SuppressWarnings("all")
    void parkTest1() throws InterruptedException {
        Object blocker = new Object();
        Thread thread = new Thread(() -> {
            System.out.println("进入线程 执行 park");
            LockSupport.park(blocker);
            System.out.println("退出线程");
        });

        thread.start();
        System.out.println("启动线程");
        Thread.sleep(1000);
        Object o = LockSupport.getBlocker(thread);
        System.out.println("Blocker: " + o);
        Assertions.assertEquals(blocker, o);
        System.out.println("unpark()");
        LockSupport.unpark(thread);
        thread.join();
    }

    /**
     *
     * <ul>
     * <li>Some other thread invokes {@link LockSupport#park() unpark} with the
     * current thread as the target; or
     *
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     *
     * <li>The specified deadline passes; or
     *
     * <li>The call spuriously (that is, for no reason) returns.
     * </ul>
     * @throws InterruptedException
     */

    @RepeatedTest(value = 10, name = "{displayName} : {currentRepetition} / {totalRepetitions}")
    @DisplayName("void parkNanos(long nanos)")
    @SuppressWarnings("all")
    void parkTest2(TestInfo testInfo) throws InterruptedException {
        System.out.println(testInfo.getDisplayName());
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            System.out.println("进入线程: " + start);

            LockSupport.parkNanos(1000 * 1000 * 100); // 100 ms

            long end = System.currentTimeMillis();
            System.out.println("退出线程: " + end);
            System.out.println("时间差： " + (end - start) + "ms");
        });

        thread.start();
        System.out.println("启动线程");
        thread.join();
    }

    @RepeatedTest(value = 10, name = "{displayName} : {currentRepetition} / {totalRepetitions}")
    @DisplayName("void parkUntil(long deadline)")
    @SuppressWarnings("all")
    void parkTest3() throws InterruptedException {
        Thread thread = new Thread(() -> {
            long start = System.currentTimeMillis();
            System.out.println("进入线程: " + start + " 执行 parkUntil");

            LockSupport.parkUntil(System.currentTimeMillis() + 100);

            long end = System.currentTimeMillis();
            System.out.println("退出线程: " + end);
            System.out.println("时间差： " + (end - start) + "ms");
        });

        thread.start();
        System.out.println("启动线程");
        thread.join();
    }

}
