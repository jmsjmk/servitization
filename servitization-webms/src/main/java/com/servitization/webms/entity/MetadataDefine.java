package com.servitization.webms.entity;

import java.util.Date;

public class MetadataDefine {

    private long id;
    private long metadataId;
    private long proxyId;
    private String sourceUrl;
    private int timeUnit;
    private int times;
    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(long metadataId) {
        this.metadataId = metadataId;
    }

    public long getProxyId() {
        return proxyId;
    }

    public void setProxyId(long proxyId) {
        this.proxyId = proxyId;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public int getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(int timeUnit) {
        this.timeUnit = timeUnit;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
