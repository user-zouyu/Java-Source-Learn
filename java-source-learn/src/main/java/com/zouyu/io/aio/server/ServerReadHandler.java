package com.zouyu.io.aio.server;

import com.zouyu.io.aio.server.base.MessageEntity;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author ZouYu 2022/10/12 15:43
 * @version 1.0.0
 */
public class ServerReadHandler implements CompletionHandler<Integer, MessageEntity> {

    private AsynchronousSocketChannel socketChannel;

    public ServerReadHandler(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void completed(Integer length, MessageEntity context) {
        if (length == -1) {
            System.out.println(context.getAddress().toString() + context.getBody());

            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void failed(Throwable exc, MessageEntity attachment) {

    }
}
