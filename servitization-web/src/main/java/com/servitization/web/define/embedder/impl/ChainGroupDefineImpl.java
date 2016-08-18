package com.servitization.web.define.embedder.impl;

import com.servitization.web.define.embedder.ChainElementDefine;
import com.servitization.web.define.embedder.ChainGroupDefine;
import com.servitization.web.define.embedder.GroupPolicy;

import java.util.List;

public class ChainGroupDefineImpl implements ChainGroupDefine {

    private String name;

    private List<ChainElementDefine> chainElementDefineList;

    private int groupProcessTimeout;

    private int groupSize;

    private GroupPolicy groupPolicy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChainElementDefine> getChainElementDefineList() {
        return chainElementDefineList;
    }

    public void setChainElementDefineList(
            List<ChainElementDefine> chainElementDefineList) {
        this.chainElementDefineList = chainElementDefineList;
    }

    public int getGroupProcessTimeout() {
        return groupProcessTimeout;
    }

    public void setGroupProcessTimeout(int groupProcessTimeout) {
        this.groupProcessTimeout = groupProcessTimeout;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public GroupPolicy getGroupPolicy() {
        return groupPolicy;
    }

    public void setGroupPolicy(GroupPolicy groupPolicy) {
        this.groupPolicy = groupPolicy;
    }

}
