package com.servitization.metadata.define.session;

import com.servitization.metadata.define.embedder.ChainHandlerDefine;

import java.util.Map;

public interface SessionDefine extends ChainHandlerDefine {

    public Map<String, String> getStrategyMap();
    
    public Map<String, StrategyEntry> getStrategEntryMap();
    

}
