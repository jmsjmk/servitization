package com.servitization.commons.business.agent.log;


public class RpcLogObject {

    private long startTime;
    private String methodName;
    private Object request;
    private Object result;
    private String sysError;
    private String bizError;
    private String header;
    private String errorMsg;
    private Throwable throwable;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getSysError() {
        return sysError;
    }

    public void setSysError(String sysError) {
        this.sysError = sysError;
    }

    public String getBizError() {
        return bizError;
    }

    public void setBizError(String bizError) {
        this.bizError = bizError;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
