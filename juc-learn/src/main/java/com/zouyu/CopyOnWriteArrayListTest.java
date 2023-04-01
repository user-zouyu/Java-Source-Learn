package com.zouyu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ZouYu 2022/11/7 10:00
 * @version 1.0.0
 */
public class CopyOnWriteArrayListTest {


    @Test
    @DisplayName("CopyOnWriteArrayList 使用")
    void test1() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        // 添加
        list.add(1);

        list.add(1, 2);


    }

}
