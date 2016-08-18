package com.servitization.proxy;

import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.proxy.TargetService;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public interface IServiceProxy {

    Object doService(ImmobileRequest request,
                            ImmobileResponse response, TargetService targetService,
                            RequestContext context) throws ClientProtocolException,
            NullPointerException, IOException, IllegalAccessException,
            URISyntaxException, InterruptedException, ExecutionException;

    void destory();
}
