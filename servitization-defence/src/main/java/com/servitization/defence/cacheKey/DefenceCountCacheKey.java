package com.servitization.defence.cacheKey;

import com.servitization.commons.cache.ICacheKey;

public class DefenceCountCacheKey implements ICacheKey {
    private static final String main = "DefenceIpPathCount";
    private final String key;
    private int expirationTime = -1;

    public DefenceCountCacheKey(String ip, String path) {
        key = String.format("%s_%s_path", main, ip, path);
    }

    @Override
    public int getExpirationTime() {
        return expirationTime;
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

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }
}
