package com.servitization.proxy;

import com.servitization.metadata.define.proxy.ThresholdType;

public interface IValveController {
    public ThresholdType type();

    public boolean condition(String info);
}
