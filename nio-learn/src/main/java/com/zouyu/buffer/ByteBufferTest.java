package com.zouyu.buffer;

import com.zouyu.utils.Utils;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @author ZouYu 2023/3/14 16:26
 * @version 1.0.0
 */
public class ByteBufferTest {

    @Test
    void test1() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();
        Utils.bufferInfo(buffer);
        byte ch = buffer.get();
        System.out.println("ch: " + ch);
        Utils.bufferInfo(buffer);
        byte[] buf = new byte[16];
        ch = buffer.get(2);
        System.out.println("ch: " + ch);

    }

    @Test
    void test2() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        buffer.put("zou yu".getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println("char: " + (char) buffer.get());
        }
    }



}
