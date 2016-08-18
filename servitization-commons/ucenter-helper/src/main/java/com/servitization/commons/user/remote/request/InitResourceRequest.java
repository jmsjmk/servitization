package com.servitization.commons.user.remote.request;

import com.servitization.commons.socket.remote.entity.RemoteRequest;
import com.servitization.commons.user.remote.common.ActionMappingInfo;

import java.util.List;

public class InitResourceRequest extends RemoteRequest {
    private static final long serialVersionUID = 1L;
    private String authKey;
    private int channel;
    private List<ActionMappingInfo> actionMappingInfos;
    private long auth;
    private boolean useSingleChannel;
    private boolean useSingleAuth;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public List<ActionMappingInfo> getActionMappingInfos() {
        return actionMappingInfos;
    }

    public void setActionMappingInfos(List<ActionMappingInfo> actionMappingInfos) {
        this.actionMappingInfos = actionMappingInfos;
    }

    public boolean isUseSingleChannel() {
        return useSingleChannel;
    }

    public void setUseSingleChannel(boolean useSingleChannel) {
        this.useSingleChannel = useSingleChannel;
    }

    public boolean isUseSingleAuth() {
        return useSingleAuth;
    }

    public void setUseSingleAuth(boolean useSingleAuth) {
        this.useSingleAuth = useSingleAuth;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public long getAuth() {
        return auth;
    }

    public void setAuth(long auth) {
        this.auth = auth;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
