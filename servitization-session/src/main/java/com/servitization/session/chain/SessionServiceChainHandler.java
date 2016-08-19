package com.servitization.session.chain;

import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.handler.ChainHandler;
import com.servitization.embedder.handler.HandleResult;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.session.SessionDefine;
import com.servitization.metadata.define.session.StrategyEntry;
import com.servitization.session.strategy.AuthResult;
import com.servitization.session.strategy.CheckStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session验证服务
 */
public class SessionServiceChainHandler implements ChainHandler {

    Map<String, CheckStrategy> strategys = null;

    /**
     * public void init(ChainElementDefine eleDefine, GlobalContext context) {
     * SessionDefine sd = (SessionDefine) eleDefine;
     * strategys = new ConcurrentHashMap<String, CheckStrategy>();
     * if (sd.getStrategyMap() != null) {
     * try {
     * for (Map.Entry<String, String> entry : sd.getStrategyMap()
     * .entrySet()) {
     * strategys.put(entry.getKey(), (CheckStrategy) (Class
     * .forName(entry.getValue()).newInstance()));
     * }
     * } catch (Exception e) {
     * throw new RuntimeException(
     * "Failed to load the session check strategys!", e);
     * }
     * }
     * AuthDataService.init(context.getEmcfContext().getBean(
     * SessionAuthHelper.class));
     * }
     */
    public void init(ChainElementDefine eleDefine, GlobalContext context) {
        SessionDefine sd = (SessionDefine) eleDefine;
        strategys = new ConcurrentHashMap<>();
        if (sd.getStrategyMap() != null) {
            try {
                for (Map.Entry<String, StrategyEntry> entry : sd.getStrategEntryMap()
                        .entrySet()) {
                    StrategyEntry se = entry.getValue();
                    CheckStrategy cs = (CheckStrategy) Class.forName(se.getValue()).newInstance();
                    if (null != cs) {
                        cs.setStrategyEntry(se);
                    }
                    strategys.put(entry.getKey(), cs);
                }
            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to load the session check strategys!", e);
            }
        }
    }

    public HandleResult handle(ImmobileRequest request,
                               ImmobileResponse response, RequestContext context) {
        String path = request.getServiceName();
        // 没配的时候，默认放过
        CheckStrategy strategy = strategys.get(path);
        if (strategy == null)
            return HandleResult.CONTINUE;
        // 只有当配了、而且校验失败时，才STOP，系统出错了也放过
        AuthResult result = strategy.checkSession(request, response, context);
        if (result != null && result.getIsError()) {
            context.addError(result.getErrorCode(), result.getErrorMessage());
            return HandleResult.STOP;
        }
        return HandleResult.CONTINUE;
    }

    public void destroy(GlobalContext context) {
        strategys = null;
    }
}
