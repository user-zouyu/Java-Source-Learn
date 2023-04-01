package com.zouyu;

import org.junit.jupiter.api.Test;

/**
 * @author ZouYu 2022/10/18 19:12
 * @version 1.0.0
 */
public class ThreadTest {

    @Test
    public void test1() throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                if (Thread.interrupted()) {
                    System.out.println("有中断标志");
                } else {
                    System.out.println("没有中断标志");
                }
            }
        });

        thread.start();
//        TimeUtils.MILLISECONDS.sleep(3);
        System.out.println("添加中断");
        thread.interrupt();

        thread.join();
    }

}
