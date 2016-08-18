package com.servitization.webms.entity;

import java.util.Date;

public class MetadataNode {

    private long id;
    private long metadataId;
    private int nodeId;
    private String nodeName;
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

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
