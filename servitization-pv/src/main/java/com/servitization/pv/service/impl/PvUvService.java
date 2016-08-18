package com.servitization.pv.service.impl;

import com.google.common.eventbus.AsyncEventBus;
import com.servitization.commons.user.remote.helper.UserHelper;
import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.embedder.ServiceDefine;
import com.servitization.pv.entity.HadoopEventObj;
import com.servitization.pv.hadoop.HadoopLogInterceptor;
import com.servitization.pv.hadoop.event.HadoopEvent;
import com.servitization.pv.hadoop.support.HadoopDemandSupport;
import com.servitization.pv.service.IPvUvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PvUvService implements IPvUvService {
    private static final Logger LOG = LoggerFactory.getLogger(PvUvService.class);
    private AsyncEventBus asyncEventBus;
    private HadoopEvent event;
    private ExecutorService threadPool;
    private ServiceDefine sd;

    public void init(ChainElementDefine eleDefine, GlobalContext context) {
        int coreNum = Runtime.getRuntime().availableProcessors();
        threadPool = Executors.newFixedThreadPool(coreNum > 0 ? coreNum : 4);
        asyncEventBus = new AsyncEventBus(threadPool);
        this.sd = context.getServiceDefine();
        HadoopLogInterceptor hadoopLogInterceptor = new HadoopLogInterceptor();
        hadoopLogInterceptor.setHadoopDemand(new HadoopDemandSupport());
        hadoopLogInterceptor.setUserHelp(context.getEmcfContext().getBean(UserHelper.class));
        event = new HadoopEvent();
        event.setLogInterceptor(hadoopLogInterceptor);
        asyncEventBus.register(event);
    }

    @Override
    public void writeHadoopLog(ImmobileRequest request) {
        try {
            HadoopEventObj eventLog = new HadoopEventObj();
            eventLog.setRequest(request);
            eventLog.setMethodName(request.getServiceName());
            eventLog.setController(sd.getName());
            eventLog.setRealClientIp(request.getRemoteIP());
            eventLog.setBusinessLine(sd.getName());
            asyncEventBus.post(eventLog);
        } catch (Exception e) {
            LOG.error("PvUvService writeHadoopLog exception:" + e);
        }
    }

    /**
     * 销毁线程池资源
     */
    public void destroy() {
        if (asyncEventBus != null)
            asyncEventBus.unregister(event);
        if (threadPool != null)
            threadPool.shutdownNow();
        asyncEventBus = null;
        threadPool = null;
        sd = null;
        event = null;
    }
}