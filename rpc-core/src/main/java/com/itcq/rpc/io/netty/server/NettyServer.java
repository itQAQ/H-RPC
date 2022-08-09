package com.itcq.rpc.io.netty.server;

import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author hwy
 * @description
 * @date 2022/8/9 20:12
 */
public class NettyServer {

    private String port;

    private int host;


    public NettyServer(String port, int host) {
        this.port = port;
        this.host = host;
    }

    public void start(){

        //
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();


    }

}
