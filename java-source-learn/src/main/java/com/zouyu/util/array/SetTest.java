package com.zouyu.util.array;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author ZouYu 2022/10/4 13:44
 * @version 1.0.0
 */
public class SetTest {
    protected transient int x = 0;

    @Test
    void test() {
        Set<String> set = new TreeSet<>();
        SetTest setTest = new SetTest();
        setTest.x = 100;
        Inter inter = setTest.getInter();

        System.out.println(inter.i_x);

        SetTest setTest1 = new SetTest();
        setTest1.x = 101;
        Inter inter1 = setTest1.getInter();

        System.out.println(inter1.i_x);
        System.out.println(inter.i_x);
    }

    Inter getInter() {
        return new Inter();
    }
    class Inter {
        int i_x = x;
    }
}
