package com.servitization.commons.user.remote.helper;

import com.servitization.commons.socket.remote.RemoteService;
import com.servitization.commons.user.remote.request.MobileChannelRequest;
import com.servitization.commons.user.remote.request.QueryEncryptKeyListReq;

public interface MobileChannelHelper {

    @RemoteService(serviceName = "headercheck/checkMobileChannel", serviceVersion = "1.0", serviceType = "ucenter")
    public String checkMobileChannel(MobileChannelRequest req);

    @RemoteService(serviceName = "headercheck/queryEncryptKeyList", serviceVersion = "1.0", serviceType = "ucenter")
    public String queryEncryptKeyList(QueryEncryptKeyListReq req);
}
