package com.servitization.metadata.define.proxy;

import com.servitization.metadata.define.XmlSerializable;
import org.dom4j.Element;

public class ServiceModule implements XmlSerializable {

    private static final long serialVersionUID = 1L;

    private String key;
    private String convert;
    private SourceService sourceService;
    private TargetService targetService;

    public ServiceModule() {
    }

    public ServiceModule(String key, SourceService sourceService, TargetService targetService) {
        this.key = key;
        this.sourceService = sourceService;
        this.targetService = targetService;
    }

    public String getConvert() {
        return convert;
    }

    public void setConvert(String convert) {
        this.convert = convert;
    }

    public String toString() {
        return String.format("[sou:%s,tar:%s]", sourceService == null ? null
                : sourceService.toString(), targetService == null ? null
                : targetService.toString());
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SourceService getSourceService() {
        return sourceService;
    }

    public void setSourceService(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    public TargetService getTargetService() {
        return targetService;
    }

    public void setTargetService(TargetService targetService) {
        this.targetService = targetService;
    }

    @Override
    public void serialize(Element parent) {
        Element me = parent.addElement(this.getClass().getSimpleName());
        me.addAttribute("key", key);
        me.addAttribute("convert", convert);
        sourceService.serialize(me);
        targetService.serialize(me);
    }

    @Override
    public void deserialize(Element self) {
        this.key = self.attributeValue("key");
        this.convert = self.attributeValue("convert");
        sourceService = new SourceService();
        sourceService.deserialize(self);
        targetService = new TargetService();
        targetService.deserialize(self);
    }
}
