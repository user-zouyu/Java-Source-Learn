package com.zouyu.treemap;

import org.junit.jupiter.api.Test;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author ZouYu 2023/3/19 20:49
 * @version 1.0.0
 */
public class TreeMapTest {
    @Test
    void test1() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();

        treeMap.put(100, "z");
        treeMap.put(1000, "0");
        treeMap.put(10, "y");

        SortedMap<Integer, String> map = treeMap.tailMap(20);
        SortedMap<Integer, String> sortedMap = treeMap.tailMap(100);
        System.out.println("tail map: " + sortedMap);
        SortedMap<Integer, String> headMap = treeMap.headMap(100);
        System.out.println("head map: " + headMap);
        sortedMap.put(100_000, "zouyu");
        System.out.println(treeMap);
        System.out.println(sortedMap.firstKey());

        System.out.println(map.firstKey());
    }

}
