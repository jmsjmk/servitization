package com.servitization.defence.cacheKey;

import com.servitization.commons.cache.ICacheKey;

public class VerityCheckCodeCachekey implements ICacheKey {

    private final String key = "CheckCode";
    private final String seperator = "_";

    private String subKey = "";

    public String getSubKey() {
        return subKey;
    }

    public void setSubKey(String subKey) {
        this.subKey = subKey;
    }

    @Override
    public int getExpirationTime() {
        return 180; // 验证码时间为3分钟
    }

    @Override
    public String getKey() {
        return key + seperator + subKey;
    }

    @Override
    public Object getValueFromSource() {
        return null;
    }

    @Override
    public int getLocalCacheTime() {
        return 0;
    }
}
