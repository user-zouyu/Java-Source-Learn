package com.zouyu;


import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author ZouYu 2022/11/5 18:07
 * @version 1.0.0
 */
public class ForkJoinPoolTest {

    @Test
    void test1() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(new SunTask(0, 10000));
        Integer res = submit.get();

        System.out.println(res);
    }

    @Test
    void test2() {
        int res = 0;
        for (int i = 0; i < 10000; i++) {
            TimeUtils.MILLISECONDS.sleep(1);
            res += i;
        }
        System.out.println(res);
    }


    private static class SunTask extends RecursiveTask<Integer> {
        private final Integer start;
        private final Integer end;

        public SunTask(Integer start, Integer end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {

            if (end - start < 1000) {
                int res = 0;
                for (int i = start; i < end; i++) {
                    res += i;
                    TimeUtils.MILLISECONDS.sleep(1);
                }
                return res;
            } else {
                int mod = start + (end - start) / 2;
                SunTask leftPart = new SunTask(start, mod);
                SunTask rightPart = new SunTask(mod, end);
                leftPart.fork();
                rightPart.fork();
                TimeUtils.MILLISECONDS.sleep(1);
                return leftPart.join() + rightPart.join();
            }
        }
    }

}
