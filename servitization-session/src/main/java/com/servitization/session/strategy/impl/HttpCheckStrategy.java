package com.servitization.session.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.servitization.commons.user.remote.common.EnumAuthResult;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.define.proxy.ServicePool;
import com.servitization.metadata.define.proxy.TargetService;
import com.servitization.metadata.define.session.StrategyEntry;
import com.servitization.proxy.IRequestConvert;
import com.servitization.proxy.converterImpl.RequestConverter;
import com.servitization.proxy.rpcclient.HttpClientFactory;
import com.servitization.session.strategy.AuthResult;
import com.servitization.session.strategy.CheckStrategy;
import com.servitization.session.util.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.HashMap;

public class HttpCheckStrategy implements CheckStrategy {

    private StrategyEntry strategyEntry;

    private HttpClientFactory httpClientFactory;

    private IRequestConvert requestConvert;

    @Override
    public AuthResult checkSession(ImmobileRequest request, ImmobileResponse response, RequestContext context) {
        // 1. token 一期可以忽略
        // String token =
        // request.getHeader(CustomHeaderEnum.SESSIONTOKEN.headerName());
        String openId = request.getParameter("openId");
        AuthResult authResult;
        if (StringUtils.isBlank(openId)) {
            authResult = new AuthResult();
            authResult.setResult(EnumAuthResult.NoPermission);
            return authResult;
        }

        // 2.
        if (null == httpClientFactory) {
            httpClientFactory = new HttpClientFactory();
        }
        if (null == requestConvert) {
            requestConvert = new RequestConverter();
        }
        // 2.1
        TargetService targetService = new TargetService();
        ServicePool servicePool = new ServicePool();
        initParam(targetService, servicePool, strategyEntry);
        try {
            HttpUriRequest httpUriRequest = requestConvert.convert2HttpUriRequest(request, targetService, servicePool);
            byte[] resByte = httpClientFactory.customHttpClient().sendRequestGetEntityBytes(httpUriRequest);
            String resStr = new String(resByte, "utf-8");
            // jsonp 跨域导致返回数据:jsonp1({\"data)\":null,\"resCode\":0,\"msg\":\"操作成功\"})
            // jsonp-->json
            // System.out.println(resStr);
            String json = JSONUtil.changeJsonp2Json(resStr);
            HashMap map = JSON.parseObject(json, HashMap.class);
            String resCode = map.get("resCode").toString();
            if (resCode.trim().equalsIgnoreCase("0")) { // 成功
                authResult = new AuthResult();
                authResult.setIsError(false);
                authResult.setAuthMessage("validate success");
            } else { // 失败
                authResult = new AuthResult();
                authResult.setResult(EnumAuthResult.NoPermission);
            }
        } catch (Exception e) {
            e.printStackTrace();
            authResult = new AuthResult();
            authResult.setResult("500", e.getMessage());
            return authResult;
        }
        // 3. 返回结果
        return authResult;
    }

    /**
     * @param targetService
     * @param servicePool
     * @param strategyEntry
     */
    public void initParam(TargetService targetService, ServicePool servicePool, StrategyEntry strategyEntry) {
        if (null == strategyEntry || null == targetService || null == servicePool) {
            return;
        }
        targetService.setMethod(strategyEntry.getHttpMethod());
        targetService.setSocketTimeout(2500);

        servicePool.setConnectTimeout(2500);
        servicePool.setUrl(strategyEntry.getUrl());
    }

    public StrategyEntry getStrategyEntry() {
        return strategyEntry;
    }

    @Override
    public void setStrategyEntry(StrategyEntry se) {
        this.strategyEntry = se;
    }
}
