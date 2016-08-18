package com.servitization.web.define.check.impl;

import com.servitization.web.define.check.CheckConf;

import java.util.Map;

public class CheckConfImpl implements CheckConf {

    private Map<String, String> StrategyMap;

    public Map<String, String> getStrategyMap() {
        return StrategyMap;
    }

    public void setStrategyMap(Map<String, String> strategyMap) {
        StrategyMap = strategyMap;
    }
}
