package com.servitization.webms.entity;

import java.util.Date;

public class MetadataVersion {

    private long id;
    private long metadataId;
    private String metadataXml;
    private String description;
    private Date createTime;
    private boolean isMachineExist;

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

    public String getMetadataXml() {
        return metadataXml;
    }

    public void setMetadataXml(String metadataXml) {
        this.metadataXml = metadataXml;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean getIsMachineExist() {
        return isMachineExist;
    }

    public void setIsMachineExist(boolean isMachineExist) {
        this.isMachineExist = isMachineExist;
    }

}
