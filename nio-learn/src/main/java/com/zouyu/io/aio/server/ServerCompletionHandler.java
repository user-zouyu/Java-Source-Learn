package com.zouyu.io.aio.server;

import com.zouyu.io.aio.server.base.MessageEntity;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author ZouYu 2022/10/12 15:37
 * @version 1.0.0
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {

    private final AsynchronousServerSocketChannel serverSocketChannel;

    private CompletionHandler<Integer, MessageEntity> readHandler;

    public ServerCompletionHandler(AsynchronousServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void completed(AsynchronousSocketChannel channel, Void attachment) {
        serverSocketChannel.accept(null, this);

        try {
            readHandler = new ServerReadHandler(channel);
            channel.read(ByteBuffer.allocate(64), new MessageEntity(channel.getRemoteAddress()), readHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        exc.printStackTrace();
    }
}
