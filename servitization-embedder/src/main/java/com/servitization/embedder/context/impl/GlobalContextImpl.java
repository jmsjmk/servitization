package com.servitization.embedder.context.impl;

import com.servitization.embedder.context.GlobalContext;
import com.servitization.metadata.define.embedder.ServiceDefine;
import org.springframework.context.ApplicationContext;

public class GlobalContextImpl implements GlobalContext {

    private ServiceDefine sd;

    private ApplicationContext emcf;

    public GlobalContextImpl(ServiceDefine sd, ApplicationContext emcf) {
        this.sd = sd;
        this.emcf = emcf;
    }

    public ServiceDefine getServiceDefine() {
        return sd;
    }

    public ApplicationContext getEmcfContext() {
        return emcf;
    }

}
