package com.itcq.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hwy
 * @description
 * @date 2022/8/10 10:27
 */
@AllArgsConstructor
@Getter
public enum SerializerCode {

    KRYO(0),
    JSON(1),
    HESSIAN(2),
    PROTOBUF(3);

    private final int code;

}
