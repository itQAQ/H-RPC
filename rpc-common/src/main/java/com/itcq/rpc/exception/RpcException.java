package com.itcq.rpc.exception;

import com.itcq.rpc.enums.RpcError;

/**
 * @author hwy
 * @description
 * @date 2022/8/10 10:21
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }

}
