package com.zouyu.util.array;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author ZouYu 2022/10/3 0:19
 * @version 1.0.0
 */
public class LinkedListTest {

    @Test
    public void test() {
        LinkedList<String> list = new LinkedList<>();
    }

    @Test
    void test2() {
        PriorityQueue<String> queue = new PriorityQueue<>();

        queue.add("a");
        queue.add("c");
        queue.add("b");

        while (!queue.isEmpty()) {
            String poll = queue.poll();
            System.out.println(poll);
        }
    }
}
