package com.servitization.defence.chain;

import com.servitization.commons.user.remote.result.DefenceRemoteResp;
import com.servitization.defence.service.TurtleDefenceService;
import com.servitization.defence.service.helper.business.DefenceBusiness;
import com.servitization.defence.service.helper.business.UserBusiness;
import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.handler.ChainHandler;
import com.servitization.embedder.handler.HandleResult;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.defence.DefenceDefine;
import com.servitization.metadata.define.embedder.ChainElementDefine;

import java.util.HashMap;
import java.util.Map;

/**
 * 防攻击服务
 */
public class DefenceServiceChainHandler implements ChainHandler {

    private final String checkUrl_key = "checkUrl";

    private TurtleDefenceService turtleDefenceService;

    public void init(ChainElementDefine eleDefine, GlobalContext context) {
        DefenceDefine dDefine = (DefenceDefine) eleDefine;
        DefenceBusiness bussiness = new DefenceBusiness();
        UserBusiness userBusiness = new UserBusiness();
        bussiness.setUserBusiness(userBusiness);
        bussiness.init(dDefine, context);
        turtleDefenceService = new TurtleDefenceService();
        turtleDefenceService.setDefenceBusiness(bussiness);
        turtleDefenceService.setWhiteList(dDefine.getIPWhiteList());
    }

    public HandleResult handle(ImmobileRequest request,
                               ImmobileResponse response, RequestContext context) {
        HandleResult result = HandleResult.CONTINUE;
        DefenceRemoteResp resp = turtleDefenceService.validate(request);
        if (resp != null && resp.getIsError()) {
            result = HandleResult.STOP;
            Map<String, String> customErrorEntity = new HashMap<>();
            customErrorEntity.put(checkUrl_key, resp.getCheckUrl());
            context.addError(resp.getErrorCode(), resp.getErrorMessage(),
                    customErrorEntity);
        }
        return result;
    }

    @Override
    public void destroy(GlobalContext context) {
        turtleDefenceService.destory();
        turtleDefenceService = null;
    }
}
