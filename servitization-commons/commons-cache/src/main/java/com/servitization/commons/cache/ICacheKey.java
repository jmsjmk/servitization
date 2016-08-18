package com.servitization.commons.cache;

/**
 * cache key 通过实例化cache key对key统一管理
 */
public interface ICacheKey {
    /**
     * cache key对key统一管理
     *
     * @return Key
     */
    String getKey();

    /**
     * cache timeout
     * 绝对过期时间（秒），-1 代表永不过期
     *
     * @return ExpirationTime
     */
    int getExpirationTime();

    /**
     * 重新从数据来源中获取数据
     *
     * @return Object
     */
    Object getValueFromSource();

    /**
     * 本地缓存时间
     *
     * @return
     */
    int getLocalCacheTime();
}
