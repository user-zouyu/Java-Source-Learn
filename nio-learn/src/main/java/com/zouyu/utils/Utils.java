package com.zouyu.utils;

import java.nio.Buffer;

/**
 * @author ZouYu 2023/3/14 16:36
 * @version 1.0.0
 */
public class Utils {
    public static void bufferInfo(Buffer buffer) {
        System.out.print("position: " + buffer.position());
        System.out.print(", limit: " + buffer.limit());
        System.out.println(", capacity: " + buffer.capacity());
    }

}
