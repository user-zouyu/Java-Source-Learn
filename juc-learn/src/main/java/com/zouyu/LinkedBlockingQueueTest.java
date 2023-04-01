package com.zouyu;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author ZouYu 2022/10/14 10:48
 * @version 1.0.0
 */
public class LinkedBlockingQueueTest {

    @Test
    @SuppressWarnings("all")
    void test() throws InterruptedException {
        LinkedBlockingQueue<String> strings = new LinkedBlockingQueue<>();
        strings.put("z");
    }

}
