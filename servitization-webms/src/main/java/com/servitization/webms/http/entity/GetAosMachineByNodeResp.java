package com.servitization.webms.http.entity;

import java.util.List;

public class GetAosMachineByNodeResp extends AosBaseResult {
    private List<AosMachine> data;

    public List<AosMachine> getData() {
        return data;
    }

    public void setData(List<AosMachine> data) {
        this.data = data;
    }
}
