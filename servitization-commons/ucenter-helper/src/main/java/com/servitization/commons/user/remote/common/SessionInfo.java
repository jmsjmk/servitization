package com.servitization.commons.user.remote.common;

import java.util.Date;

public class SessionInfo {

    private String password;
    private long cardNo;
    private String authNo;
    private int type;
    private Date createTime;
    private int sessionTime;
    private String deviceId;
    private byte status;
    private String sessionToken;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthNo() {
        return authNo;
    }

    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getCardNo() {
        return cardNo;
    }

    public void setCardNo(long cardNo) {
        this.cardNo = cardNo;
    }

    public int getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
