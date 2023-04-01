package com.zouyu.io.aio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @author ZouYu 2022/10/12 15:19
 * @version 1.0.0
 */
public class ServerBoot {
    public static void main(String[] args) throws IOException, InterruptedException {
        AioServer aioServer = new AioServer(
                Executors.newFixedThreadPool(1),
                new InetSocketAddress("0.0.0.0", 8080)
        );

        aioServer.run(args);

        Thread.sleep(1000 * 60 * 2);
    }
}
