package com.servitization.session.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.servitization.commons.user.remote.common.EnumAuthResult;
import com.servitization.embedder.context.RequestContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.embedder.immobile.ImmobileResponse;
import com.servitization.metadata.common.Constants;
import com.servitization.metadata.common.CustomHeaderEnum;
import com.servitization.metadata.define.session.StrategyEntry;
import com.servitization.session.service.AuthDataService;
import com.servitization.session.strategy.AuthResult;
import com.servitization.session.strategy.CheckStrategy;
import com.servitization.session.util.TokenValidateHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ForceCheckWithVersionStrategy implements CheckStrategy {

    private static Map<String, String> clientVersionMap = new HashMap<>();

    static {
        clientVersionMap.put("7", "7");
        clientVersionMap.put("3", "6.9.0");
        clientVersionMap.put("4", "4.0.0");
        clientVersionMap.put("1", "7.0.0");
    }

    @Override
    public AuthResult checkSession(ImmobileRequest request,
                                   ImmobileResponse response, RequestContext context) {
        String clientType = request.getHeader(CustomHeaderEnum.CLIENTTYPE
                .headerName());
        String version = request.getHeader(CustomHeaderEnum.VERSION
                .headerName());
        if (!checkClientAndVersion(clientType, version))
            return new AuthResult();
        String token = request.getHeader(CustomHeaderEnum.SESSIONTOKEN
                .headerName());
        AuthResult authResult;
        if (StringUtils.isBlank(token)) {
            authResult = new AuthResult();
            authResult.setResult(EnumAuthResult.NoPermission);
            return authResult;
        }
        if (!TokenValidateHelper.validate(token)) {
            authResult = new AuthResult();
            authResult.setResult(EnumAuthResult.NotValid);
            return authResult;
        }
        authResult = AuthDataService.validateToken(token);
        if (authResult.getCardNo() > 0) {
            String req = request.getParameter(Constants.REQ_PARAM_NAME);
            JSONObject reqJsonObject = JSON.parseObject(req);
            reqJsonObject.put("cardNo", "" + authResult.getCardNo());
            request.setParameter(Constants.REQ_PARAM_NAME, JSON.toJSONString(reqJsonObject));
        }
        return authResult;
    }

    private boolean checkClientAndVersion(String clientType, String version) {
        if (clientVersionMap == null)
            return false;
        String minVersion = clientVersionMap.get(clientType);
        if (StringUtils.isBlank(minVersion))
            return false;
        if (StringUtils.equalsIgnoreCase(clientType, minVersion)) {
            return true;
        }
        String[] mins = StringUtils.split(minVersion, '.');
        String[] vers = StringUtils.split(version, '.');
        int i = 0;
        for (i = 0; i < mins.length && i < vers.length; i++) {
            if (mins[i].length() != vers[i].length()) {
                return vers[i].length() > mins[i].length();
            } else {
                int compare = vers[i].compareTo(mins[i]);
                if (compare != 0) {
                    return compare > 0;
                }
            }
        }
        if (i < vers.length) {
            return true;
        }
        if (i < mins.length) {
            return false;
        }
        return true;
    }

    @Override
    public void setStrategyEntry(StrategyEntry se) {
    }
}
