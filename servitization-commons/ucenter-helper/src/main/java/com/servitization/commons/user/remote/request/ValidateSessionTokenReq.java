package com.servitization.commons.user.remote.request;

import com.servitization.commons.socket.remote.entity.RemoteRequest;
import com.servitization.commons.util.husky.FieldDes;

import java.util.List;

public class ValidateSessionTokenReq extends RemoteRequest {

    private static final long serialVersionUID = 1L;

    @FieldDes("session")
    private String token;
    private int type;
    @FieldDes(value = "接口路径_HTTPMETHOD,/train/orderlist_GET", isNecessary = false)
    private List<String> paths;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }
}
