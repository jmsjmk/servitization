package com.servitization.commons.business.agent.business;

import com.servitization.commons.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AreaCodeConfig {

    private static final Lock lock = new ReentrantLock();
    private static Properties properties = null;

    private AreaCodeConfig() {
    }

    /**
     * 获取区号配置文件
     *
     * @return
     */
    public static Properties getProperties() {
        if (properties == null) {
            lock.lock();
            try {
                if (properties == null) {
                    properties = PropertiesUtil.load("config/agent-config.properties");
                }

                return properties;
            } finally {
                lock.unlock();
            }
        } else {
            return properties;
        }
    }

    /**
     * 获取区号配置文件值
     *
     * @param key key值
     * @return
     */
    public static String getValue(String key) {
        if (StringUtils.isBlank(key)) {
            return StringUtils.EMPTY;
        }
        Properties p = getProperties();
        return p.getProperty(key);
    }

    /**
     * 获取区号配置文件值
     *
     * @param key          key值
     * @param defaultValue 默认值
     * @return
     */
    public static String getValue(String key, String defaultValue) {
        if (StringUtils.isBlank(key)) {
            return defaultValue;
        }
        Properties p = getProperties();
        return p.getProperty(key, defaultValue);
    }


}
