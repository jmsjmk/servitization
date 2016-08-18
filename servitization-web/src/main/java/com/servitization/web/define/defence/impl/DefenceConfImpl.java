package com.servitization.web.define.defence.impl;

import com.servitization.web.define.defence.DefenceConf;

import java.util.Map;

public class DefenceConfImpl implements DefenceConf {

    private Map<String, String> StrategyMap;

    public Map<String, String> getStrategyMap() {
        return StrategyMap;
    }

    public void setStrategyMap(Map<String, String> strategyMap) {
        StrategyMap = strategyMap;
    }

}
