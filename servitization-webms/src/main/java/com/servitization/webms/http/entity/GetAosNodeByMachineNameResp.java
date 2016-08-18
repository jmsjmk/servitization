package com.servitization.webms.http.entity;

import java.util.List;

public class GetAosNodeByMachineNameResp extends AosBaseResult {

    private List<AosNode> data;

    public List<AosNode> getData() {
        return data;
    }

    public void setData(List<AosNode> data) {
        this.data = data;
    }
}
