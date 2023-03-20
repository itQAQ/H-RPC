package com.itcq.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hwy
 * @description
 * @date 2022/8/10 10:26
 */
@AllArgsConstructor
@Getter
public enum PackageType {

    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;

}