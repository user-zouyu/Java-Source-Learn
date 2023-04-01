package com.zouyu.buffer;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

/**
 * @author ZouYu 2023/3/15 18:19
 * @version 1.0.0
 */
public class IntBufferTest {

    @Test
    void test() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        CharBuffer charBuffer = buffer.asCharBuffer();
        charBuffer.put("hello");
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.println("byte: " + (char)buffer.get());
        }

        charBuffer.flip();
        while (charBuffer.hasRemaining()) {
            System.out.println("char:" + charBuffer.get());
        }
    }

}
