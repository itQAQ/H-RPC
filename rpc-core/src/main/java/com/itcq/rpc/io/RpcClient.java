package com.itcq.rpc.io;

import com.itcq.rpc.dto.RpcRequest;
import com.itcq.rpc.serializer.CommonSerializer;

/**
 * @author hwy
 * @description
 * @date 2023/3/20 15:33
 */
public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    Object sendRequest(RpcRequest rpcRequest);
}
