package com.servitization.pv.entity;

import com.servitization.commons.cache.ICacheKey;

public class UserKey implements ICacheKey {
    private String key = "MobileChannelOrderFrom";

    @Override
    public int getExpirationTime() {

        return 0;
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
