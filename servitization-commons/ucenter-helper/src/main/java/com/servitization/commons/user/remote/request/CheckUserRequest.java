package com.servitization.commons.user.remote.request;

import com.servitization.commons.socket.remote.entity.RemoteRequest;

public class CheckUserRequest extends RemoteRequest {

    private static final long serialVersionUID = 6325228736177028772L;

    private String deviceId;
    private String clientType;
    private String mac;
    private String cardNumber;

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

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


}
