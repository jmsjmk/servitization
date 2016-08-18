package com.servitization.web.define.proxy;

public class SourceService {
    private String path;
    private String httpMethod;
    private int timeout;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String toString() {
        return String.format("%s_%s", path, httpMethod);
    }
}
