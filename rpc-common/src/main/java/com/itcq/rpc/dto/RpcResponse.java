package com.itcq.rpc.dto;

import com.itcq.rpc.enums.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author hwy
 * @description
 * @date 2022/8/10 10:29
 */
@Data
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {

    /**
     * 响应对应的请求号
     */
    private String requestId;
    /**
     * 响应状态码
     */
    private Integer statusCode;
    /**
     * 响应状态补充信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }

}
