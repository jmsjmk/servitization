package com.servitization.request.chain;

import com.servitization.commons.user.remote.helper.MobileChannelHelper;
import com.servitization.commons.user.remote.result.MobileChannelResponse;
import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.handler.ChainHandler;
import com.servitization.embedder.handler.HandleResult;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.request.service.MobileChannelService;
import com.servitization.request.service.RequestCheckService;

/**
 * 请求合法性检查
 */
public class RequestServiceChainHandler implements ChainHandler {

    private RequestCheckService checkService;

    public void init(ChainElementDefine eleDefine, GlobalContext context) {
        checkService = new RequestCheckService();
        MobileChannelService mobileChannelService = new MobileChannelService();
        mobileChannelService.setHelper(context.getEmcfContext().getBean(
                MobileChannelHelper.class));
        checkService.setChannelService(mobileChannelService);
        mobileChannelService.init();
    }

    public HandleResult handle(ImmobileRequest request,
                               ImmobileResponse response, RequestContext context) {
        HandleResult result = HandleResult.CONTINUE;
        MobileChannelResponse resp = checkService.checkRequest(request);
        if (resp != null && (!resp.getIsValid() || resp.getIsError())) {
            result = HandleResult.STOP;
            context.addError(resp.getErrorCode(), resp.getErrorMessage());
        }
        return result;
    }

    @Override
    public void destroy(GlobalContext context) {
        checkService.destory();
    }
}
