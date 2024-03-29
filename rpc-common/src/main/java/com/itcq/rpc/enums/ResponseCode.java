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
public enum ResponseCode {

    SUCCESS(200, "调用方法成功"),
    FAIL(500, "调用方法失败"),
    METHOD_NOT_FOUND(500, "未找到指定方法"),
    CLASS_NOT_FOUND(500, "未找到指定类");

    private final int code;
    private final String message;

}