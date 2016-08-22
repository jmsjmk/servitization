package com.servitization.embedder.core;

import com.servitization.embedder.Worker;
import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.impl.RequestContextImpl;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.embedder.ServiceDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import java.io.IOException;

public class WorkerImpl implements Worker {

    private static final Logger LOG = LoggerFactory.getLogger(WorkerImpl.class);

    private GlobalContext context = null;

    private ServiceDefine define = null;

    private Chain upChain = null;

    private Chain downChain = null;

    public void init(GlobalContext context) {
        this.context = context;
        this.define = context.getServiceDefine();
        if (define == null)
            throw new RuntimeException("Can't find the service define from the resource context!");
        upChain = new Chain();
        upChain.init(define.getUpChainList(), context);
        downChain = new Chain();
        downChain.init(define.getDownChainList(), context);
    }

    public void process(ImmobileRequest request, ImmobileResponse response, FilterChain chain) {
        RequestContextImpl reqContext = new RequestContextImpl();
        reqContext.createRequestContext(context);
        upChain.exec(request, response, reqContext);
        downChain.exec(request, response, reqContext);
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader();
            response.flushContent();
        } catch (IOException e) {
            LOG.error(reqContext.getRequestContext().getGlobalContext().getServiceDefine().getName()
                    + "#Error occurs when writing response!");
        }
    }

    public void destory() {
        upChain.destory(context);
        downChain.destory(context);
        context = null;
    }
}
