package com.servitization.metadata.define.embedder.impl;

import com.servitization.metadata.define.embedder.ChainHandlerDefine;
import org.dom4j.Element;

public class ChainHandlerDefineImpl implements ChainHandlerDefine {

    private static final long serialVersionUID = 1L;

    private String name;

    private String handlerClazz;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getHandlerClazz() {
        return handlerClazz;
    }

    public void setHandlerClazz(String handlerClazz) {
        this.handlerClazz = handlerClazz;
    }

    @Override
    public void serialize(Element parent) {
        Element me = parent.addElement(this.getClass().getName());
        me.addAttribute("name", name);
        me.addAttribute("handlerClazz", handlerClazz);
    }

    @Override
    public void deserialize(Element self) {
        this.name = self.attributeValue("name");
        this.handlerClazz = self.attributeValue("handlerClazz");
    }
}
