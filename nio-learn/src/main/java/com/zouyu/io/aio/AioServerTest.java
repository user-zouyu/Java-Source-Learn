package com.zouyu.io.aio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ZouYu 2022/10/12 14:28
 * @version 1.0.0
 */
public class AioServerTest {
    private static final Object waitObject = new Object();

    @Test
    @DisplayName("AIO Server")
    void server1() throws InterruptedException, IOException {

        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        AsynchronousChannelGroup group = AsynchronousChannelGroup.withThreadPool(threadPool);
        final AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open(group);

        serverSocket.bind(new InetSocketAddress("0.0.0.0", 8080));

        serverSocket.accept(null, new ServerSocketChannelHandle(serverSocket));


        synchronized (waitObject) {
            waitObject.wait();
        }
    }
    static class ServerSocketChannelHandle implements CompletionHandler<AsynchronousSocketChannel, Void> {

        private final AsynchronousServerSocketChannel serverSocketChannel;

        public ServerSocketChannelHandle(AsynchronousServerSocketChannel serverSocketChannel) {
            this.serverSocketChannel = serverSocketChannel;
        }

        @Override
        public void completed(AsynchronousSocketChannel socketChannel, Void attachment) {
            this.serverSocketChannel.accept(attachment, this);

            ByteBuffer readBuffer = ByteBuffer.allocate(64);
            StringBuffer stringBuffer = new StringBuffer();
            try {
                SocketAddress remoteAddress = socketChannel.getRemoteAddress();
                System.out.println(remoteAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
            socketChannel.read(readBuffer, stringBuffer, new SocketChannelReadHandle(socketChannel, readBuffer));

        }

        @Override
        public void failed(Throwable exc, Void attachment) {
            exc.printStackTrace();
        }
    }

    static class SocketChannelReadHandle implements CompletionHandler<Integer, StringBuffer> {

        private final AsynchronousSocketChannel socketChannel;

        private final ByteBuffer byteBuffer;

        public SocketChannelReadHandle(AsynchronousSocketChannel socketChannel, ByteBuffer byteBuffer) {
            this.socketChannel = socketChannel;
            this.byteBuffer = byteBuffer;
        }

        @Override
        public void completed(Integer length, StringBuffer historyContext) {
            //如果条件成立，说明客户端主动终止了TCP套接字，这时服务端终止就可以了
            if (length == -1) {
                try {
                    this.socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            this.byteBuffer.flip();
            byte[] contexts = new byte[64];
            this.byteBuffer.get(contexts, 0, length);
            this.byteBuffer.clear();
            String nowContent = new String(contexts, 0, length, StandardCharsets.UTF_8);
            historyContext.append(nowContent);
            System.out.println("temp: " + nowContent);

            this.socketChannel.read(this.byteBuffer, historyContext, this);
        }

        /* (non-Javadoc)
         * @see java.nio.channels.CompletionHandler#failed(java.lang.Throwable, java.lang.Object)
         */
        @Override
        public void failed(Throwable exc, StringBuffer historyContext) {
            System.out.println("=====发现客户端异常关闭，服务器将关闭TCP通道");
            try {
                this.socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

