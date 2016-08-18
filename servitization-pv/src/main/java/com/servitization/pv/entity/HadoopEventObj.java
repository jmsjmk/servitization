package com.servitization.pv.entity;

import com.servitization.embedder.immobile.ImmobileRequest;

public class HadoopEventObj {
    public ImmobileRequest getRequest() {
        return request;
    }

    public void setRequest(ImmobileRequest request) {
        this.request = request;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getRealClientIp() {
        return realClientIp;
    }

    public void setRealClientIp(String realClientIp) {
        this.realClientIp = realClientIp;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    private ImmobileRequest request;
    private String controller;
    private String methodName;
    private String realClientIp;
    /*----对应于开放平台元数据xml文件中节点ServiceDefineImpl的name属性的值----------*/
    private String businessLine;
}
