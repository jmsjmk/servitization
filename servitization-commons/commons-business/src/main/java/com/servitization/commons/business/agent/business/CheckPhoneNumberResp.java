package com.servitization.commons.business.agent.business;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class CheckPhoneNumberResp implements Serializable {

    @JSONField(name = "Success")
    private int success;

    @JSONField(name = "Message")
    private String message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CheckPhoneNumberResp{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
