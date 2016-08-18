package com.servitization.commons.socket.remote.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RemoteRequest implements Serializable {

    private static final long serialVersionUID = 7652260478633258184L;

    private Map<String, Object> global = new HashMap<>();

    public Map<String, Object> getGlobal() {
        return global;
    }

    public void setGlobal(Map<String, Object> global) {
        this.global.putAll(global);
    }

    public void putPro(String key, Object value) {
        this.global.put(key, value);
    }

    public Object getPro(String key) {
        return this.global.get(key);
    }
}
