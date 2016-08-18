package com.servitization.commons.user.remote.result;

import com.servitization.commons.user.remote.common.SessionInfo;

public class ValidateSessionTokenResp extends BaseResult {
    private SessionInfo sessionInfo;

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }
}
