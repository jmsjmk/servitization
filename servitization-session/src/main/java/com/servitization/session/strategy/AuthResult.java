package com.servitization.session.strategy;

import com.alibaba.fastjson.annotation.JSONField;
import com.servitization.commons.user.remote.common.EnumAuthResult;
import com.servitization.commons.user.remote.result.BaseResult;

public class AuthResult extends BaseResult {

    private String authMessage;
    private String authNo;
    private long cardNo;

    private String openId;
    private String telphone;
    private int type;

    private String userName;

    public String getAuthMessage() {
        return authMessage;
    }

    @JSONField(serialize = false)
    public String getAuthNo() {
        return authNo;
    }

    @JSONField(serialize = false)
    public long getCardNo() {
        return cardNo;
    }

    public String getOpenId() {
        return openId;
    }

    public String getTelphone() {
        return telphone;
    }

    @JSONField(serialize = false)
    public int getType() {
        return type;
    }

    public String getUserName() {
        return userName;
    }

    public void setAuthMessage(String authMessage) {
        this.authMessage = authMessage;
    }

    public void setAuthNo(String authNo) {
        this.authNo = authNo;
    }

    public void setCardNo(long cardNo) {
        this.cardNo = cardNo;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setResult(EnumAuthResult enumAuthResult) {
        setIsError(true);
        setErrorCode(enumAuthResult.getCode());
        setErrorMessage(enumAuthResult.getMessage());
    }

    public void setResult(String code, String message) {
        setIsError(true);
        setErrorCode(code);
        setErrorMessage(message);
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
