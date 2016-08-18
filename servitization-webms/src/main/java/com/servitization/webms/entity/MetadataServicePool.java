package com.servitization.webms.entity;

public class MetadataServicePool {

    private long id;
    private String servicePoolName;
    private String url;
    private int serviceType;
    private double coefficient;
    private int forceCloseChannel;
    private long forceCloseTimeMillis;
    private int connectTimeout;
    private String createTime;
    private String oldServicePoolName;//更新时使用
    private long metadataId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServicePoolName() {
        return servicePoolName;
    }

    public void setServicePoolName(String servicePoolName) {
        this.servicePoolName = servicePoolName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getForceCloseChannel() {
        return forceCloseChannel;
    }

    public void setForceCloseChannel(int forceCloseChannel) {
        this.forceCloseChannel = forceCloseChannel;
    }

    public long getForceCloseTimeMillis() {
        return forceCloseTimeMillis;
    }

    public void setForceCloseTimeMillis(long forceCloseTimeMillis) {
        this.forceCloseTimeMillis = forceCloseTimeMillis;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public String getOldServicePoolName() {
        return oldServicePoolName;
    }

    public void setOldServicePoolName(String oldServicePoolName) {
        this.oldServicePoolName = oldServicePoolName;
    }

    public long getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(long metadataId) {
        this.metadataId = metadataId;
    }

}
