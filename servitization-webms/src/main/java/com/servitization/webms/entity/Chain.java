package com.servitization.webms.entity;

public class Chain {

    private Metadata metadata;
    private MetadataModule metadataModule;
    private MetadataGroup metadataGroup;

    /**
     * 0:module 1:group
     */
    private int type;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public MetadataModule getMetadataModule() {
        return metadataModule;
    }

    public void setMetadataModule(MetadataModule metadataModule) {
        this.metadataModule = metadataModule;
    }

    public MetadataGroup getMetadataGroup() {
        return metadataGroup;
    }

    public void setMetadataGroup(MetadataGroup metadataGroup) {
        this.metadataGroup = metadataGroup;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
