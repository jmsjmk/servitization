package com.servitization.webms.entity;

import java.util.Date;

public class MetadataPublish {
    private long id;
    private long nodeRelationId;
    private long metadataVersionId;
    private int status;
    private Date createTime;
    private String versionDesc;
    private String versionId;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNodeRelationId() {
        return nodeRelationId;
    }

    public void setNodeRelationId(long nodeRelationId) {
        this.nodeRelationId = nodeRelationId;
    }

    public long getMetadataVersionId() {
        return metadataVersionId;
    }

    public void setMetadataVersionId(long metadataVersionId) {
        this.metadataVersionId = metadataVersionId;
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


}
