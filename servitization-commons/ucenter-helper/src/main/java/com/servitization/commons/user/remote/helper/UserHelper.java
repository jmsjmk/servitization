package com.servitization.commons.user.remote.helper;

import com.servitization.commons.socket.remote.RemoteService;
import com.servitization.commons.user.remote.request.*;

public interface UserHelper {

    @RemoteService(serviceName = "user/getHadoopHeaderInfo", serviceVersion = "1.0", serviceType = "ucenter")
    String getHadoopHeaderInfo(GetHadoopHeaderInfoReq req);
}
