package com.servitization.commons.user.remote.request;

import com.servitization.commons.socket.remote.entity.RemoteRequest;

public class GetAuthorityResourceReq extends RemoteRequest {

    private static final long serialVersionUID = 1L;

    private int channel;

    private boolean notUseCache;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean isNotUseCache() {
        return notUseCache;
    }

    public void setNotUseCache(boolean notUseCache) {
        this.notUseCache = notUseCache;
    }
}
