package com.servitization.commons.user.remote.helper;

import com.servitization.commons.socket.remote.RemoteService;
import com.servitization.commons.user.remote.request.GetAuthorityResourceReq;
import com.servitization.commons.user.remote.request.InitResourceRequest;
import com.servitization.commons.user.remote.request.MobileChannelRequest;
import com.servitization.commons.user.remote.request.ValidateSessionTokenReq;

public interface SessionAuthHelper {

    @RemoteService(serviceName = "session/validateSessionToken", serviceVersion = "1.0", serviceType = "ucenter")
    String validateSessionToken(ValidateSessionTokenReq req);
}
