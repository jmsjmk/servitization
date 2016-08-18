package com.servitization.webms.entity;

import java.util.Date;

public class MetadataPublishIp {
    private long id;
    private long publishId;
    private String ip;
    private int status;
    private Date createTime;
    private Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPublishId() {
        return publishId;
    }

    public void setPublishId(long publishId) {
        this.publishId = publishId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MetadataPublishIp{" +
                "id=" + id +
                ", publishId=" + publishId +
                ", ip='" + ip + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
