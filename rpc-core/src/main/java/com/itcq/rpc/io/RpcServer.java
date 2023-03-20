package com.itcq.rpc.io;

import com.itcq.rpc.serializer.CommonSerializer;

/**
 * @author hwy
 * @description
 * @date 2022/11/18 14:48
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    <T> void publishService(T service, String serviceName);
}
