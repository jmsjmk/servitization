package com.servitization.webms.entity;

import java.util.Date;
import java.util.List;

public class MetadataGroup {

    private long id;
    private long metadataId;
    private String name;
    private int processTimeOut;
    private int size;
    private String policy;
    private String moduleIds;
    /**
     * 所属上下行链条 0:上行 1:下行
     */
    private int chain;
    private Date createTime;

    private List<MetadataModule> metadataModules;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProcessTimeOut() {
        return processTimeOut;
    }

    public void setProcessTimeOut(int processTimeOut) {
        this.processTimeOut = processTimeOut;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getModuleIds() {
        return moduleIds;
    }

    public void setModuleIds(String moduleIds) {
        this.moduleIds = moduleIds;
    }

    public int getChain() {
        return chain;
    }

    public void setChain(int chain) {
        this.chain = chain;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<MetadataModule> getMetadataModules() {
        return metadataModules;
    }

    public void setMetadataModules(List<MetadataModule> metadataModules) {
        this.metadataModules = metadataModules;
    }
}
