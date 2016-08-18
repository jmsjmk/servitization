package com.servitization.proxy.valve;

import com.servitization.metadata.define.proxy.ThresholdType;
import com.servitization.proxy.IValveController;
import org.apache.commons.lang3.RandomUtils;

/**
 * 直接地按比率进行限流
 */
public class SimpleValve implements IValveController {

    private int threshold; // 流量阀值

    public SimpleValve(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean condition(String pass_null) {
        if (threshold == 0) {
            return false;
        }
        if (threshold == 100) {
            return true;
        }
        //threadHold = 80,80% return true;20% return false;
        return RandomUtils.nextInt(0, 100) < threshold;
    }

    @Override
    public ThresholdType type() {
        return ThresholdType.BYPERCENTAGE;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
