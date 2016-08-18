package com.servitization.web.define.proxy;

public class ServiceModule {
    private String key;
    private SourceService sourceService;
    private TargetService targetService;

    public ServiceModule() {
    }

    public ServiceModule(String key, SourceService sourceService, TargetService targetService) {
        this.key = key;
        this.sourceService = sourceService;
        this.targetService = targetService;
    }

    public String toString() {
        return String.format("[sou:%s,tar:%s]", sourceService == null ? null : sourceService.toString(),
                targetService == null ? null : targetService.toString());
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
}
