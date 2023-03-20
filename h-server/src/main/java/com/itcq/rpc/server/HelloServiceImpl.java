package com.itcq.rpc.server;

import com.itcq.rpc.annotation.MyService;
import com.itcq.rpc.api.HelloObject;
import com.itcq.rpc.api.HelloServiceApi;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hwy
 * @description
 * @date 2023/3/20 15:47
 */
@MyService
@Slf4j
public class HelloServiceImpl implements HelloServiceApi {

    @Override
    public String hello(HelloObject object) {
        log.info("收到客户端请求,方法名:{},请求信息:{}","hello()",object.getMessage());
        return "服务端响应:你好！";
    }
}
