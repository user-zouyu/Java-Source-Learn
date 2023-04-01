package com.zouyu.io.bio;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author ZouYu 2022/10/12 14:19
 * @version 1.0.0
 */
public class SocketClientTest {

    @RepeatedTest(1)
    public void client1() throws IOException, InterruptedException {
        Socket socket = null;
        OutputStreamWriter outputStreamWriter = null;

        for (int i = 0; i < 100; i++) {
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress("127.0.0.1", 8080));
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                outputStreamWriter.write("zouyu: " + i + "  ");
                outputStreamWriter.flush();
                Thread.sleep(1000);
            } finally {
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                    outputStreamWriter = null;
                }
            }
        }
    }


    @Test
    void client2() throws IOException {
        int socketNum = 1000;
        Socket[] sockets = new Socket[socketNum];
        OutputStreamWriter[] writers = new OutputStreamWriter[socketNum];

        for (int i = 0; i < socketNum; i++) {
            sockets[i] = new Socket();
            sockets[i].connect(new InetSocketAddress("127.0.0.1", 8080));
            writers[i] = new OutputStreamWriter(sockets[i].getOutputStream());
        }


        for (int i = 0; i < socketNum; i++) {
            final OutputStreamWriter writer = writers[i];
            writer.write("zouyu: " + i);
            writer.flush();
        }

        for (int i = 0; i < socketNum; i++) {
            if(writers[i] != null) {
                writers.clone();
                writers[i] = null;
            }

            if(sockets[i] != null) {
                sockets[i].close();
                sockets[i] = null;
            }
        }
    }

}
