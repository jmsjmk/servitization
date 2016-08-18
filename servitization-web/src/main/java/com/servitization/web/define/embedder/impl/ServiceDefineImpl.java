package com.servitization.web.define.embedder.impl;

import com.servitization.web.define.embedder.ChainElementDefine;
import com.servitization.web.define.embedder.DeployModel;
import com.servitization.web.define.embedder.ServiceDefine;

import java.util.List;

public class ServiceDefineImpl implements ServiceDefine {

    private String name;

    private DeployModel deployModel;

    private List<ChainElementDefine> upChainList;

    private List<ChainElementDefine> downChainList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeployModel getDeployModel() {
        return deployModel;
    }

    public void setDeployModel(DeployModel deployModel) {
        this.deployModel = deployModel;
    }

    public List<ChainElementDefine> getUpChainList() {
        return upChainList;
    }

    public void setUpChainList(List<ChainElementDefine> upChainList) {
        this.upChainList = upChainList;
    }

    public List<ChainElementDefine> getDownChainList() {
        return downChainList;
    }

    public void setDownChainList(List<ChainElementDefine> downChainList) {
        this.downChainList = downChainList;
    }

}
