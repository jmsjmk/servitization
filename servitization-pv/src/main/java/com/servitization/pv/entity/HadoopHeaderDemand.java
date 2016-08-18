package com.servitization.pv.entity;

public class HadoopHeaderDemand {
    private String channelId;
    private int clientType;
    private int orderFormType;

    public int getOrderFormType() {
        return orderFormType;
    }

    public void setOrderFormType(int orderFormType) {
        this.orderFormType = orderFormType;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }


}
