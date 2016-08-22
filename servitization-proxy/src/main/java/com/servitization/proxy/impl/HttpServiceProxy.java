package com.servitization.proxy.impl;

import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.proxy.ServicePool;
import com.servitization.metadata.define.proxy.TargetService;
import com.servitization.proxy.CommonLogger;
import com.servitization.proxy.IRequestConvert;
import com.servitization.proxy.IServiceMapping;
import com.servitization.proxy.IServiceProxy;
import com.servitization.proxy.converterImpl.RequestConverter;
import com.servitization.proxy.rpcclient.HttpClientFactory;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class HttpServiceProxy implements IServiceProxy {

    private HttpClientFactory httpClientFactory;
    private IRequestConvert requestConvert;
    private IServiceMapping serviceMapper;

    public HttpServiceProxy(IServiceMapping serviceMapper) {
        this.httpClientFactory = new HttpClientFactory();
        this.requestConvert = new RequestConverter();
        this.serviceMapper = serviceMapper;
        CommonLogger.getLogger().info("HttpServiceProxy init");
    }

    @Override
    public Object doService(ImmobileRequest request, ImmobileResponse response, TargetService targetService, RequestContext context)
            throws NullPointerException, IOException, IllegalAccessException, URISyntaxException, InterruptedException, ExecutionException {
        ServicePool servicePool = serviceMapper.getServicePoolInfo(targetService.getServicePoolName());
        HttpUriRequest httpRequest = requestConvert.convert2HttpUriRequest(request, targetService, servicePool);
        Integer code = httpClientFactory.customHttpClient().sendRequest(httpRequest, response.getOutputStream());
        return code;
    }

    @Override
    public void destory() {
        httpClientFactory = null;
        requestConvert = null;
    }
}
