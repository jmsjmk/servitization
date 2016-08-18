package com.servitization.metadata.define.defence;

import com.servitization.metadata.define.embedder.ChainHandlerDefine;

import java.util.Map;
import java.util.Set;

public interface DefenceDefine extends ChainHandlerDefine {

    public Map<String, String> getStrategyMap();

    public Set<String> getIPWhiteList();

}
