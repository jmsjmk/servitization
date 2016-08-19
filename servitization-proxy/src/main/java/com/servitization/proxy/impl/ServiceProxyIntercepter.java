package com.servitization.proxy.impl;

import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.common.CustomHeaderEnum;
import com.servitization.metadata.define.proxy.ProxyDefine;
import com.servitization.metadata.define.proxy.ServicePool;
import com.servitization.metadata.define.proxy.TargetService;
import com.servitization.proxy.*;
import com.servitization.proxy.converterImpl.ResponseConverter;
import com.servitization.proxy.sourceImpl.ServiceDefinitionContainer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class ServiceProxyIntercepter implements IServiceProxy {

    private IServiceMapping serviceMapping;

    private IServiceProxy[] serviceProxies;

    private IResponseConvert responseConvert;

    public ServiceProxyIntercepter(ProxyDefine pd, GlobalContext context) {
        serviceMapping = new ServiceDefinitionContainer(pd, context);
        responseConvert = new ResponseConverter();
        loadProxy(3);
    }

    public void loadProxy(int max_index) {
        IServiceProxy[] temp = new IServiceProxy[max_index];
        this.serviceProxies = temp;
        temp[0] = new HttpServiceProxy(serviceMapping);
        temp[1] = new EmcfServiceProxy(serviceMapping.getRemoteClient());
        temp[2] = new AsynHttpServiceProxy(serviceMapping);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < max_index; i++) {
            buffer.append("index:")
                    .append(i)
                    .append(",")
                    .append(temp[i] == null ? null : temp[i].getClass()
                            .getName()).append(";\t");
        }
        CommonLogger.getLogger().info(
                String.format("ProxyIntercepter load with [%s]",
                        buffer.toString()));
    }

    /**
     * <p>
     * 1.根据请求获取目标服务 path 查找 TargetService  <br/>
     * <p>
     * 2.TargetService 开始拒绝流量 <br/>
     * <p>
     * 3.通过TargetService 获取池的信息 ,通过 servicePoolName <br/>
     * <p>
     * 4.根据池的 serviceType 决定用什么代理 0 一期写死 按照http代理来进行处理
     */
    @Override
    public Object doService(ImmobileRequest request,
                            ImmobileResponse response, TargetService pass_null,
                            RequestContext context) throws
            NullPointerException, IllegalAccessException, IOException,
            URISyntaxException, InterruptedException, ExecutionException {
        TargetService targetService = serviceMapping.getTargetService(request);

        if (targetService == null) {
            throw new IllegalAccessException("there is no service mapping#"
                    + request.getServiceName());
        }
        IValveController valve = serviceMapping.getValve(targetService);
        // 拒绝多少的流量
        if (valve != null) {
            switch (valve.type()) {
                case BYPERCENTAGE:
                    if (valve.condition(null))
                        return 500;
                    break;
                case BYUSER:
                    String deviceId = request
                            .getHeader(CustomHeaderEnum.DEVICEID.headerName());
                    if (valve.condition(deviceId))
                        return 500;
                    break;
                default:
                    break;
            }
        }
        Object obj;
        // 一期项目直接按照http代理方式进行 发送后期修改
        ServicePool servicePool = serviceMapping
                .getServicePoolInfo(targetService.getServicePoolName());
        int serviceType = servicePool.getServiceType();
        if (serviceType >= 0) {
            obj = serviceProxies[serviceType].doService(request, response,
                    targetService, context);
            if (serviceMapping.isResConvertService(request.getServiceName())) {
                responseConvert.convertResponseContext(response, request.getServiceName());
            }
        } else {
            throw new IllegalArgumentException("Invalid service type # " + serviceType);
        }
        return obj;
    }

    @Override
    public void destory() {
        serviceMapping.destory();
        serviceMapping = null;
        for (IServiceProxy proxy : serviceProxies) {
            proxy.destory();
        }
        serviceProxies = null;
    }
}
