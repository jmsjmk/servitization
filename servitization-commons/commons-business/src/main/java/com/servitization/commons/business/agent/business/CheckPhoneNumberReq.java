package com.servitization.commons.business.agent.business;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class CheckPhoneNumberReq implements Serializable {

    @JSONField(name = "BT")
    private String businessType;

    @JSONField(name = "PNo")
    private String phoneNumber;

    @JSONField(name = "L")
    private int language;

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }


}
