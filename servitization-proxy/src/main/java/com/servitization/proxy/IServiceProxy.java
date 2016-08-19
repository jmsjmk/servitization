package com.servitization.proxy;

import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.proxy.TargetService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public interface IServiceProxy {

    Object doService(ImmobileRequest request,
                     ImmobileResponse response, TargetService targetService,
                     RequestContext context) throws NullPointerException, IOException, IllegalAccessException,
            URISyntaxException, InterruptedException, ExecutionException;

    void destory();
}
