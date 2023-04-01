package com.zouyu;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author ZouYu 2022/10/18 22:32
 * @version 1.0.0
 */
public class ArrayBlockingQueueTest {

    @Test
    @SuppressWarnings("all")
    public void test1() throws InterruptedException {
        ArrayBlockingQueue<String> strings = new ArrayBlockingQueue<>(10);

        // q: hello
        strings.put("z");


    }

}
