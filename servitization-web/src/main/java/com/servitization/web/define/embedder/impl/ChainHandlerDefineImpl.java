package com.servitization.web.define.embedder.impl;

import com.servitization.web.define.embedder.ChainHandlerDefine;

public class ChainHandlerDefineImpl implements ChainHandlerDefine {

    private String name;

    private String handlerClazz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandlerClazz() {
        return handlerClazz;
    }

    public void setHandlerClazz(String handlerClazz) {
        this.handlerClazz = handlerClazz;
    }

}
