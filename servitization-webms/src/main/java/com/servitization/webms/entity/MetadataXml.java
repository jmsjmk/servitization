package com.servitization.webms.entity;

public class MetadataXml {

    private long id;
    private long versionId;
    private String metadataXml;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    public String getMetadataXml() {
        return metadataXml;
    }

    public void setMetadataXml(String metadataXml) {
        this.metadataXml = metadataXml;
    }
}
