package com.zouyu;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author ZouYu 2022/10/31 10:36
 * @version 1.0.0
 */
public class DelayQueueTest {
    @Test
    void test2() throws InterruptedException {

        DelayQueue<DelayTask> delayTasks = new DelayQueue<>();
        DelayTask delayTask = new DelayTask(10, TimeUnit.SECONDS);

        delayTasks.add(delayTask);

        System.out.println(delayTask.getDelay(TimeUnit.NANOSECONDS));

        System.out.println(new Date());
        while (!delayTasks.isEmpty()) {
            DelayTask take = delayTasks.take();
            System.out.println(new Date());
            long delay = take.getDelay(TimeUnit.SECONDS);
            System.out.println(delay);
        }
    }

    public static class DelayTask implements Delayed {


        private final long endTime;

        public DelayTask(long delay, TimeUnit unit) {
            this.endTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delay, unit);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(endTime - System.nanoTime(), TimeUnit.NANOSECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            long selfDelay = getDelay(TimeUnit.SECONDS);
            long oDelay = o.getDelay(TimeUnit.SECONDS);
            if (selfDelay < oDelay) {
                return 1;
            } else if (selfDelay > oDelay) {
                return -1;
            }
            return 0;
        }
    }
}
