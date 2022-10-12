package com.zouyu.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author ZouYu 2022/10/5 18:44
 * @version 1.0.0
 */
public class FutureTaskTest {

    @Test
    void test() throws ExecutionException, InterruptedException {
        FutureTask<String> task = new FutureTask<>(() -> "zou yu");
        task.run();
        System.out.println("commit");

        String s = task.get();

        System.out.println(s);
    }

    @Test
    void test2() {

    }
}
