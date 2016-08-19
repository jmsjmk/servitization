package com.servitization.defence.service;

import com.servitization.commons.user.remote.request.DefenceRemoteReq;
import com.servitization.commons.user.remote.result.DefenceRemoteResp;
import com.servitization.defence.service.helper.business.DefenceBusiness;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.metadata.common.CustomHeaderEnum;

import java.util.Set;

public class TurtleDefenceService {

    private DefenceBusiness defenceBusiness;

    private Set<String> whiteList;

    public DefenceRemoteResp validate(ImmobileRequest request) {
        String clientIP = request.getRemoteIP();
        // 白名单不验证
        if (whiteList != null && whiteList.contains(clientIP)) {
            return null;
        }
        String path = request.getServiceName();
        if (!isPathDefenceOpen(path)) {
            return null;
        }
        DefenceRemoteReq req = new DefenceRemoteReq();
        req.setCode(request.getHeader(CustomHeaderEnum.CHECKCODE.headerName()));
        req.setDeviceId(request.getHeader(CustomHeaderEnum.DEVICEID
                .headerName()));
        req.setIp(clientIP);
        req.setPath(path);
        DefenceRemoteResp resp = defenceBusiness.validate(req);
        return resp;
    }

    public boolean isPathDefenceOpen(String path) {
        return defenceBusiness.isPathDefenceOpen(path);
    }

    public void setDefenceBusiness(DefenceBusiness defenceBusiness) {
        this.defenceBusiness = defenceBusiness;
    }

    public void setWhiteList(Set<String> whiteList) {
        this.whiteList = whiteList;
    }

    public void destory() {
        defenceBusiness.destory();
    }
}
