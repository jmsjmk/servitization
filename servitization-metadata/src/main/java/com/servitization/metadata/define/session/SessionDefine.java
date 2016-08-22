package com.servitization.metadata.define.session;

import com.servitization.metadata.define.embedder.ChainHandlerDefine;

import java.util.Map;

public interface SessionDefine extends ChainHandlerDefine {

    Map<String, String> getStrategyMap();

    Map<String, StrategyEntry> getStrategEntryMap();
}
