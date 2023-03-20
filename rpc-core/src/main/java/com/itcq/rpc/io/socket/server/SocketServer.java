package com.itcq.rpc.io.socket.server;

import com.itcq.rpc.factory.ThreadPoolFactory;
import com.itcq.rpc.handler.RequestHandler;
import com.itcq.rpc.hook.ShutdownHook;
import com.itcq.rpc.io.AbstractRpcServer;
import com.itcq.rpc.provider.ServiceProviderImpl;
import com.itcq.rpc.register.NacosServiceRegistry;
import com.itcq.rpc.serializer.CommonSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @author hwy
 * @description
 * @date 2023/1/31 18:10
 */
@Slf4j
public class SocketServer extends AbstractRpcServer {

    /**
     * 请求处理线程池
     */
    private final ExecutorService threadPool;

    private final CommonSerializer serializer;

    private final RequestHandler requestHandler=new RequestHandler();

    public SocketServer(String host,int port){
        this(host,port,DEFAULT_SERIALIZER);
    }

    public SocketServer(String host,int port,Integer serializer) {
        this.host=host;
        this.port=port;
        threadPool= ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = new NacosServiceRegistry();
        this.serviceProvider = new ServiceProviderImpl();
        this.serializer = CommonSerializer.getByCode(serializer);
        scanService();
    }


    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(host, port));
            log.info("服务器启动……");
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("服务器启动时有错误发生:", e);
        }
    }
}
