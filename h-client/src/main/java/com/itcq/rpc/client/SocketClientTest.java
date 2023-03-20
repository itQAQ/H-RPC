package com.itcq.rpc.client;

import com.itcq.rpc.api.HelloObject;
import com.itcq.rpc.api.HelloServiceApi;
import com.itcq.rpc.io.RpcClientProxy;
import com.itcq.rpc.io.socket.client.SocketClient;
import com.itcq.rpc.serializer.CommonSerializer;

/**
 * @author hwy
 * @description
 * @date 2023/3/20 15:39
 */
public class SocketClientTest {
    public static void main(String[] args) {
        SocketClient socketClient = new SocketClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy proxy = new RpcClientProxy(socketClient);
        HelloServiceApi helloServiceApi = proxy.getProxy(HelloServiceApi.class);
        HelloObject object = new HelloObject(1, "这是客户端请求");
        String response = helloServiceApi.hello(object);
        System.out.print(response);
    }
}
