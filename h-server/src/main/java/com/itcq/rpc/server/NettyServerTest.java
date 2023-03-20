package com.itcq.rpc.server;

import com.itcq.rpc.annotation.ServiceScan;
import com.itcq.rpc.io.netty.server.NettyServer;
import com.itcq.rpc.serializer.CommonSerializer;

/**
 * @author hwy
 * @description
 * @date 2022/8/9 20:00
 */
@ServiceScan
public class NettyServerTest {

    public static void main(String[] args) {

        //1.初始化服务端配置
        NettyServer nettyServer = new NettyServer(8090, "127.0.0.1", CommonSerializer.PROTOBUF_SERIALIZER);
        //2.启动服务端
        nettyServer.start();
    }
}
