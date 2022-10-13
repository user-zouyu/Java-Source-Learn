package com.zouyu.io.aio.server;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;

/**
 * @author ZouYu 2022/10/12 15:20
 * @version 1.0.0
 */
public class AioServer {

    private final ExecutorService executorService;

    private AsynchronousServerSocketChannel serverSocketChannel;

    private final AsynchronousChannelGroup channelGroup;
    private final SocketAddress address;

    private CompletionHandler<AsynchronousSocketChannel, ? super Void> completionHandler;

    private Thread thread;


    public AioServer(ExecutorService executorService,
                     SocketAddress address) throws IOException {
        this.executorService = executorService;
        this.channelGroup = AsynchronousChannelGroup.withThreadPool(executorService);
        this.address = address;
    }

    public void shutdown() {

    }

    public void run(String[] args) throws IOException {

        thread= Thread.currentThread();
        serverSocketChannel = AsynchronousServerSocketChannel.open(channelGroup);

        completionHandler = new ServerCompletionHandler(serverSocketChannel);
        serverSocketChannel.accept(null, completionHandler);
    }



}
