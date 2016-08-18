package com.servitization.commons.user.remote.helper;

import com.servitization.commons.socket.remote.RemoteService;
import com.servitization.commons.user.remote.request.GetAuthorityResourceReq;
import com.servitization.commons.user.remote.request.InitResourceRequest;
import com.servitization.commons.user.remote.request.MobileChannelRequest;
import com.servitization.commons.user.remote.request.ValidateSessionTokenReq;

public interface SessionAuthHelper {
    @RemoteService(serviceName = "session/getSessionToken", serviceVersion = "1.0", serviceType = "ucenter")
    String getSessionToken(MobileChannelRequest req);

    @RemoteService(serviceName = "session/getAuthorityResource", serviceVersion = "1.0", serviceType = "ucenter")
    String getAuthorityResource(GetAuthorityResourceReq req);

    @RemoteService(serviceName = "session/validateSessionToken", serviceVersion = "1.0", serviceType = "ucenter")
    String validateSessionToken(ValidateSessionTokenReq req);

    @RemoteService(serviceName = "session/verifySession4Client", serviceVersion = "1.0", serviceType = "ucenter")
    String verifySession4Client(ValidateSessionTokenReq req);

    @RemoteService(serviceName = "session/initResource", serviceVersion = "1.0", serviceType = "ucenter")
    String initResource(InitResourceRequest req);
}
