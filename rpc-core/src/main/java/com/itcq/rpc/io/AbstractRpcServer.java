package com.itcq.rpc.io;

import com.itcq.rpc.annotation.MyService;
import com.itcq.rpc.annotation.ServiceScan;
import com.itcq.rpc.enums.RpcError;
import com.itcq.rpc.exception.RpcException;
import com.itcq.rpc.provider.ServiceProvider;
import com.itcq.rpc.register.ServiceRegistry;
import com.itcq.rpc.utils.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @author hwy
 * @description
 * @date 2022/8/10 11:20
 */
@Slf4j
public abstract class AbstractRpcServer implements RpcServer{

    protected String host;
    protected int port;

    protected ServiceRegistry serviceRegistry;
    protected ServiceProvider serviceProvider;

    public void scanService(){
        //获取启动类的包路径
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;

        //校验启动类是否添加@ServiceScan注解
        try {
            startClass = Class.forName(mainClassName);
            if (!startClass.isAnnotationPresent(ServiceScan.class)){
                log.info("启动类缺少@ServiceScan注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
        } catch (ClassNotFoundException e) {
            log.error("启动服务端扫描注册service，未知异常");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }

        //获取Service类所在的包路径
        String basePackge = startClass.getAnnotation(ServiceScan.class).value();
        if ("".equals(basePackge)){
            basePackge = mainClassName.substring(0, mainClassName.lastIndexOf("."));
        }

        Set<Class<?>> serviceList = ReflectUtil.getClasses(basePackge);
        for(Class<?> clazz : serviceList) {
            if(clazz.isAnnotationPresent(MyService.class)) {
                String serviceName = clazz.getAnnotation(MyService.class).name();
                Object obj;
                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error("创建 " + clazz + " 时有错误发生");
                    continue;
                }
                if("".equals(serviceName)) {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> oneInterface: interfaces){
                        publishService(obj, oneInterface.getCanonicalName());
                    }
                } else {
                    publishService(obj, serviceName);
                }
            }
        }
    }

    @Override
    public <T> void publishService(T service, String serviceName) {
        //添加本地注册表
        //将服务注册到nacos
        serviceProvider.addServiceProvider(service, serviceName);
        serviceRegistry.register(serviceName, new InetSocketAddress(host, port));

    }
}
