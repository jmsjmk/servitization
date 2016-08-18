package com.servitization.defence.cacheKey;

import com.servitization.commons.cache.ICacheKey;

public class IpwhiteListCacheKey implements ICacheKey {

    private static final String key = "ipWhiteListCacheKey";

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public int getExpirationTime() {
        return -1;
    }

    @Override
    public Object getValueFromSource() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getLocalCacheTime() {
        // TODO Auto-generated method stub
        return 0;
    }

}
