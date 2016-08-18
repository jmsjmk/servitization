package com.servitization.webms.entity;

import java.util.Date;

public class MetadataPvUv implements Comparable<MetadataPvUv> {
    private long id;
    private long metadataId;
    private long proxyId;
    private Date createTime;
    private String sourceUrl;

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public int compareTo(MetadataPvUv o) {

        return this.sourceUrl.compareTo(o.getSourceUrl());
    }
}
