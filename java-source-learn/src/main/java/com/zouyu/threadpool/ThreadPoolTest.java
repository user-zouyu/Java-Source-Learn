package com.zouyu.threadpool;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author ZouYu 2022/10/9 22:24
 * @version 1.0.0
 */
public class ThreadPoolTest {

    private static ThreadPoolExecutor executor;

    @BeforeAll
    @SuppressWarnings("all")
    public static void init() {
        executor = new ThreadPoolExecutor(
                2,
                4,
                100, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    @RepeatedTest(10)
    @DisplayName("ThreadPoolExecutor")
    void test1() {

        executor.execute(() -> System.out.println("Hello Thread Pool"));

    }


    @RepeatedTest(10)
    @DisplayName("submit")
    void test2() throws ExecutionException, InterruptedException {
        Future<String> submit = executor.submit(() -> "zou yu");

        System.out.println(submit.get());
    }
}
