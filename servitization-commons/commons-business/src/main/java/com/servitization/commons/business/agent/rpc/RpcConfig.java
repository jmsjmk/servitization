package com.servitization.commons.business.agent.rpc;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RpcConfig {
    private static final Lock lock = new ReentrantLock();
    private Map<String, String> configs = new HashMap<>();
    private static RpcConfig rpcConfig = null;

    private RpcConfig(Properties properties) {
        if (properties != null) {
            Enumeration e = properties.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = (String) properties.get(key);
                configs.put(key, value);
            }
        }
    }

    public static RpcConfig getRpcConfig(Properties properties) {
        if (rpcConfig == null) {
            lock.lock();
            try {
                if (rpcConfig == null) {
                    rpcConfig = new RpcConfig(properties);
                }
                return rpcConfig;
            } finally {
                lock.unlock();
            }
        } else {
            return rpcConfig;
        }
    }

    public String get(String key) {
        return configs.get(key);
    }
}
