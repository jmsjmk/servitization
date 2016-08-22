package com.servitization.webms.entity;

import java.util.Date;

public class MetadataProxy implements Comparable<MetadataProxy> {

    private long id;
    private long metadataId;
    private String sourceUrl;
    private String sourceMethod;
    private int isForward;
    private String targetUrl;
    private String targetMethod;
    private int connectTimeout;
    private int socketTimeout;
    private int threshold;
    private int seviceType;
    private Date createTime;
    private String serviceName;
    private String serviceVersion;
    private String servicePoolName;
    private int thresholdType;
    private String convert;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConvert() {
        return convert;
    }

    public void setConvert(String convert) {
        this.convert = convert;
    }

    public long getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(long metadataId) {
        this.metadataId = metadataId;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceMethod() {
        return sourceMethod;
    }

    public void setSourceMethod(String sourceMethod) {
        this.sourceMethod = sourceMethod;
    }

    public int getIsForward() {
        return isForward;
    }

    public void setIsForward(int isForward) {
        this.isForward = isForward;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getSeviceType() {
        return seviceType;
    }

    public void setSeviceType(int seviceType) {
        this.seviceType = seviceType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getServicePoolName() {
        return servicePoolName;
    }

    public void setServicePoolName(String servicePoolName) {
        this.servicePoolName = servicePoolName;
    }

    public int getThresholdType() {
        return thresholdType;
    }

    public void setThresholdType(int thresholdType) {
        this.thresholdType = thresholdType;
    }

    @Override
    public int compareTo(MetadataProxy o) {
        return this.sourceUrl.compareTo(o.getSourceUrl());
    }
}
