package com.servitization.commons.user.remote.request;

import com.servitization.commons.socket.remote.entity.RemoteRequest;

public class GetHadoopHeaderInfoReq extends RemoteRequest {

    private static final long serialVersionUID = 6080852135831460712L;

    private String channelId;
    private int orderFormType;
    private int clientType;
    private int deviceType;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getOrderFormType() {
        return orderFormType;
    }

    public void setOrderFormType(int orderFormType) {
        this.orderFormType = orderFormType;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }
}
