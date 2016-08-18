package com.servitization.commons.user.remote.common;

public class ActionMappingInfo {
    private String path;
    private String method;
    private long auth;
    private int channel;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public long getAuth() {
        return auth;
    }

    public void setAuth(long auth) {
        this.auth = auth;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }
}
