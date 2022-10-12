package com.zouyu.util.array;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

/**
 * @author ZouYu 2022/10/4 14:02
 * @version 1.0.0
 */
public class BitSetTest {

    @Test
    void test() {
        BitSet set = new BitSet();
        set.set(100);
        set.flip(10);

        System.out.println(set);
    }

}
