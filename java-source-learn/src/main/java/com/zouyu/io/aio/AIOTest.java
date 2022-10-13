package com.zouyu.io.aio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZouYu 2022/10/12 13:11
 * @version 1.0.0
 */
public class AIOTest {

    private static final Object wait = new Object();

    @Test
    void test1() throws IOException, InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(threadPool);

        AsynchronousServerSocketChannel socketChannel = AsynchronousServerSocketChannel.open(group);
        socketChannel.bind(new InetSocketAddress("0.0.0.0", 8080));
        socketChannel.accept(null, new MessageHandler(socketChannel));

        synchronized (wait) {
            System.out.println("等待接收消息.....");
            wait.wait();
        }
    }


    static class MessageHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {

        private AsynchronousServerSocketChannel serverSocketChannel;

        public MessageHandler(AsynchronousServerSocketChannel serverSocketChannel) {
            this.serverSocketChannel = serverSocketChannel;
        }

        @Override
        public void completed(AsynchronousSocketChannel channel, Void attachment) {
            serverSocketChannel.accept(attachment, this);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            channel.read(byteBuffer, new StringBuffer(), new ServerChannelReadHandler(channel, byteBuffer));
            System.out.println("completed()");
        }

        @Override
        public void failed(Throwable exc, Void attachment) {
            exc.printStackTrace();
            System.out.println("failed");
            System.out.println(attachment);
        }
    }

    public static class ServerChannelReadHandler implements CompletionHandler<Integer, StringBuffer> {

        private AsynchronousSocketChannel channel;

        private ByteBuffer buffer;

        public ServerChannelReadHandler(AsynchronousSocketChannel channel, ByteBuffer buffer) {
            this.channel = channel;
            this.buffer = buffer;
        }

        @Override
        public void completed(Integer length, StringBuffer context) {
            if(length != -1) {
                try {
                    System.out.println(context.toString());
                    channel.close();
                    return;
                } catch (IOException e) {
                    System.out.println("channel close error");
                }
            }

            buffer.flip();
            byte[] bytes = new byte[1024];
            buffer.get(bytes);
            buffer.clear();

            context.append(new String(bytes, 0, length, StandardCharsets.UTF_8));

            this.channel.read(buffer, context, this);
        }

        @Override
        public void failed(Throwable exc, StringBuffer attachment) {
            exc.printStackTrace();
            System.out.println(attachment.toString());
        }
    }

}
