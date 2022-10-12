package com.zouyu.util.array;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * @author ZouYu 2022/10/1 11:42
 * @version 1.0.0
 */
public class ArrayListTest {

    @Test
    void listTest(){
        ArrayList<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add(1, "ddd");

        System.out.println(list);
    }

}
