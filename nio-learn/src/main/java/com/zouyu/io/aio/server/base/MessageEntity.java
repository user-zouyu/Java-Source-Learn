package com.zouyu.io.aio.server.base;

import java.net.SocketAddress;

/**
 * @author ZouYu 2022/10/12 15:46
 * @version 1.0.0
 */
public class MessageEntity {

    private SocketAddress address;

    private StringBuffer body;

    public MessageEntity(SocketAddress address) {
        this.address = address;
        this.body = new StringBuffer();
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }

    public StringBuffer getBody() {
        return body;
    }

    public void setBody(StringBuffer body) {
        this.body = body;
    }
}
