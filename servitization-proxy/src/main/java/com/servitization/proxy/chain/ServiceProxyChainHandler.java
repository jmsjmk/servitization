package com.servitization.proxy.chain;

import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.handler.ChainHandler;
import com.servitization.embedder.handler.HandleResult;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.metadata.define.proxy.ProxyDefine;
import com.servitization.proxy.CommonLogger;
import com.servitization.proxy.IServiceProxy;
import com.servitization.proxy.impl.ServiceProxyIntercepter;
import com.servitization.proxy.log.ServitizationLogObj;
import com.servitization.proxy.log.ServitizationLogUtil;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 请求转发
 */
public class ServiceProxyChainHandler implements ChainHandler {

    private static final int SC_OK = 200;

    public static final int SC_NOT_FOUND = 404;

    public static final int SC_INIT_VAL = 999999;

    private IServiceProxy serviceProxy;

    @Override
    public void init(ChainElementDefine eleDefine, GlobalContext context) {
        serviceProxy = new ServiceProxyIntercepter((ProxyDefine) eleDefine,
                context);
    }

    @Override
    public HandleResult handle(ImmobileRequest request, ImmobileResponse response, RequestContext context) {
        ServitizationLogObj logObj = ServitizationLogUtil.getServitizationLogObj(request);
        Exception t = null;
        int code = SC_INIT_VAL;
        long startTime = 0L;
        HandleResult result = HandleResult.CONTINUE;
        try {
            startTime = System.currentTimeMillis();
            code = (int) serviceProxy.doService(request, response, null, context);
        } catch (NullPointerException e) {
            t = e;
        } catch (IllegalAccessException e) {
            t = e;
            code = SC_NOT_FOUND;
        } catch (IOException e) {
            t = e;
        } catch (URISyntaxException e) {
            t = e;
        } catch (Exception e) {
            t = e;
        }
        if (code == SC_NOT_FOUND) {
            String msg;
            if (t != null && t.getMessage() != null) {
                msg = "接口不存在." + t.getMessage();
            } else {
                msg = "接口不存在";
            }
            context.addError("703", msg);
        } else if (t != null || code != SC_OK) {
            context.addError(t);
            result = HandleResult.STOP;
            String msg = ServitizationLogUtil.getServitizationLogMsg(request, startTime, code + "");
            CommonLogger.getLogger().error(msg);
        }
        ServitizationLogUtil.writeServitizationLog(logObj,
                context.getGlobalContext().getServiceDefine().getName(), response);
        ServitizationLogUtil.writeBigLog(logObj,
                context.getGlobalContext().getServiceDefine().getName(), response);
        return result;
    }

    @Override
    public void destroy(GlobalContext context) {
        serviceProxy.destory();
        serviceProxy = null;
    }
}
