package com.servitization.pv.chain;

import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.handler.ChainHandler;
import com.servitization.embedder.handler.HandleResult;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.pvUv.PvUvDefine;
import com.servitization.pv.service.impl.PvUvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PvUvChainHandler implements ChainHandler {
    private static final Logger LOG = LoggerFactory
            .getLogger(PvUvChainHandler.class);
    private PvUvService pvUvService;
    private List<String> pvUvList = null;

    @Override
    public void init(ChainElementDefine eleDefine, GlobalContext context) {
        LOG.info("PvUvChainHandler is initing");
        PvUvDefine pvUvDefine = (PvUvDefine) eleDefine;
        pvUvList = pvUvDefine.getPvUv();
        pvUvService = new PvUvService();
        pvUvService.init(eleDefine, context);
    }

    @Override
    public HandleResult handle(ImmobileRequest request,
                               ImmobileResponse response, RequestContext context) {
        /**
         * 包含要记录pv、uv日志的接口名称时候执行
         */
        try {
            if (pvUvList != null && pvUvList.size() > 0
                    && pvUvList.contains(request.getServiceName()))
                pvUvService.writeHadoopLog(request);
        } catch (Exception e) {
            LOG.error("PvUvChainHandler error", e);
        }
        return HandleResult.CONTINUE;
    }

    @Override
    public void destroy(GlobalContext context) {
        pvUvList = null;
        if (pvUvService != null)
            pvUvService.destroy();
        pvUvService = null;
    }

}