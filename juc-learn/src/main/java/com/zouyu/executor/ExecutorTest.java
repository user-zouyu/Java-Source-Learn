package com.zouyu.executor;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author ZouYu 2023/3/25 14:08
 * @version 1.0.0
 */
public class ExecutorTest {

    @Test
    void test1() {
        ExecutorService service = Executors.newFixedThreadPool(5);
    }

    @Test
    void test2() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                10,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        Future<String> submit = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello";
            }
        });

        String s = submit.get();
        System.out.println(s);
    }

}
