package com.servitization.commons.user.remote.request;

import com.servitization.commons.socket.remote.entity.RemoteRequest;

public class MobileChannelRequest extends RemoteRequest {

    private static final long serialVersionUID = -3144307380680310868L;
    
    //渠道号
    private String channelId;
    //客户端类型
    private String clientType;
    //设备Id
    private String deviceId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}
