package com.servitization.defence.cacheKey;

import com.servitization.commons.cache.ICacheKey;

public class DefenceBlackIpCachekey implements ICacheKey {

    private static final String main = "DefenceBlackIp";
    private final String key;

    public DefenceBlackIpCachekey(String subKey, String path) {
        key = String.format("%s_%s_%s", main, subKey, path);
    }

    @Override
    public int getExpirationTime() {
        return 3600;  //一小时
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public int getLocalCacheTime() {
        return 0;
    }

    @Override
    public Object getValueFromSource() {
        return null;
    }
}
