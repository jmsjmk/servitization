package com.servitization.webms.entity;

import java.util.Date;

public class MetadataSession {
    private Date createTime;
    private long id;
    private long metadataId;
    private long proxyId;
    private String reqType;
    private String sourceUrl;
    private String strategy;
    private String strategyImpClass;

    private String validateMethod;

    private String validateurl;

    public Date getCreateTime() {
        return createTime;
    }

    public long getId() {
        return id;
    }

    public long getMetadataId() {
        return metadataId;
    }

    public long getProxyId() {
        return proxyId;
    }

    public String getReqType() {
        return reqType;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getStrategy() {
        return strategy;
    }

    public String getStrategyImpClass() {
        return strategyImpClass;
    }

    public String getValidateMethod() {
        return validateMethod;
    }

    public String getValidateurl() {
        return validateurl;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMetadataId(long metadataId) {
        this.metadataId = metadataId;
    }

    public void setProxyId(long proxyId) {
        this.proxyId = proxyId;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public void setStrategyImpClass(String strategyImpClass) {
        this.strategyImpClass = strategyImpClass;
    }

    public void setValidateMethod(String validateMethod) {
        this.validateMethod = validateMethod;
    }

    public void setValidateurl(String validateurl) {
        this.validateurl = validateurl;
    }

}
