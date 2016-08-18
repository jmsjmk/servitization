package com.servitization.proxy.valve;

import com.servitization.metadata.define.proxy.ThresholdType;
import com.servitization.proxy.IValveController;
import org.apache.commons.lang3.StringUtils;

public class UserValve implements IValveController {

    private int threshold; // 流量阀值

    public UserValve(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean condition(String deviceId) {
        if (threshold == 0) {
            return false;
        }
        if (threshold == 100) {
            return true;
        }
        if (StringUtils.isBlank(deviceId)) {
            return true;
        }

        //threadHold = 80,80% return true;20% return false;
        return hashValue(deviceId) < threshold;
    }

    @Override
    public ThresholdType type() {
        return ThresholdType.BYUSER;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    // BKDR Hash Algorithm
    private int hashValue(String str) {

        long seed = 131;
        long hash = 0;
        char[] charArray = str.toCharArray();
        for (char ch : charArray) {
            hash = hash * seed + ch;
        }
        return (int) ((hash & 0x7FFFFFFF) % 100);
    }
}
