package com.servitization.webms.entity;

import java.util.Date;

public class MetadataMachine {

    private long id;
    private long metadataId;
    private String ip;
    private String status;
    private Date createTime;

    public void setId(long id) {
        this.id = id;
    }

    public void setMetadataId(long metadataId) {
        this.metadataId = metadataId;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getMetadataId() {

        return metadataId;
    }

    public String getIp() {
        return ip;
    }

    public String getStatus() {
        return status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public long getId() {

        return id;
    }
}
