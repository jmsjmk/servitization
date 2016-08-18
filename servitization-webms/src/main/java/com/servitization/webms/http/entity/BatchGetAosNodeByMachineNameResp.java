package com.servitization.webms.http.entity;

import java.util.List;
import java.util.Map;

public class BatchGetAosNodeByMachineNameResp extends AosBaseResult {
    private Map<String, List<AosNode>> data;

    public Map<String, List<AosNode>> getData() {
        return data;
    }

    public void setData(Map<String, List<AosNode>> data) {
        this.data = data;
    }
}
