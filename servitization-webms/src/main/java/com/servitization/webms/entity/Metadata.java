package com.servitization.webms.entity;

import java.util.Date;

public class Metadata {

    private long id;
    private String metaKey;
    private String description;
    private String deployModel;
    private String upChain;
    private String downChain;
    private Date createTime;
    private Date timeStamp;

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeployModel() {
        return deployModel;
    }

    public void setDeployModel(String deployModel) {
        this.deployModel = deployModel;
    }

    public String getUpChain() {
        return upChain;
    }

    public void setUpChain(String upChain) {
        this.upChain = upChain;
    }

    public String getDownChain() {
        return downChain;
    }

    public void setDownChain(String downChain) {
        this.downChain = downChain;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
