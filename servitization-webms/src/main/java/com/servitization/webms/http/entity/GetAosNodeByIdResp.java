package com.servitization.webms.http.entity;

public class GetAosNodeByIdResp extends AosBaseResult {

    private AosNode data;

    public AosNode getData() {
        return data;
    }

    public void setData(AosNode data) {
        this.data = data;
    }
}
