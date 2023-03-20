package com.itcq.rpc.server;

import com.itcq.rpc.annotation.ServiceScan;
import com.itcq.rpc.io.socket.server.SocketServer;
import com.itcq.rpc.serializer.CommonSerializer;

/**
 * @author hwy
 * @description
 * @date 2023/1/29 14:54
 */
@ServiceScan
public class SocketServerTest {

    public static void main(String[] args) {
        SocketServer server = new SocketServer("127.0.0.1", 9527, CommonSerializer.HESSIAN_SERIALIZER);
        server.start();
    }
}
