package com.itcq.rpc.io.netty.server;

import com.itcq.rpc.serializer.CommonDecoder;
import com.itcq.rpc.serializer.CommonEncoder;
import com.itcq.rpc.serializer.CommonSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author hwy
 * @description
 * @date 2022/8/9 20:12
 */
@Slf4j
public class NettyServer {

    private int port;

    private String host;

    private CommonSerializer serializer;

    public NettyServer(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public NettyServer(int port, String host, Integer serializer) {
        this.port = port;
        this.host = host;
        this.serializer = CommonSerializer.getByCode(serializer);

        //开启服务扫描
    }

    public void start(){

        //1.创建bossGroup线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //2.创建workerGroup线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //3.创建服务端启动助手
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //4.设置bossGroup和workGroup线程组
            serverBootstrap.group(bossGroup, workerGroup)
                    //5.设置服务端通道实现为NIO
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //6.创建一个通道初始化对象
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //7.向pipeline中添加自定义的业务处理handler
                            pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS))
                                    //设置编解码方式
                                    .addLast(new CommonEncoder(serializer))
                                    .addLast(new CommonDecoder())
                                    .addLast(new NettyServerHandler());
                        }
                    });
            //8.启动服务端并绑定端口，同时将异步改为同步
            ChannelFuture future = serverBootstrap.bind(host,port).sync();
            //9.1关闭通道
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("启动服务器异常",e);
        } finally {
            //9.2关闭bossGroup，workerGroup线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

}
