package com.servitization.webms.entity;

import java.util.Date;

public class MetadataModule {
    private long id;
    private String name;
    private String handlerName;
    private String handlerClazz;
    private int chain;
    private int validStatus;
    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getHandlerClazz() {
        return handlerClazz;
    }

    public void setHandlerClazz(String handlerClazz) {
        this.handlerClazz = handlerClazz;
    }

    public int getChain() {
        return chain;
    }

    public void setChain(int chain) {
        this.chain = chain;
    }

    public int getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(int validStatus) {
        this.validStatus = validStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
