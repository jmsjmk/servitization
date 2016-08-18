package com.servitization.commons.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFactory {

    private static final Logger logger = LoggerFactory.getLogger(ConfigFactory.class);
    private static Properties redisConfig = null;

    static {
        InputStream in = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            redisConfig = new Properties();
            in = classLoader.getResourceAsStream("config/redis.properties");
            redisConfig.load(in);
            in.close();
        } catch (IOException e) {
            logger.error("ConfigFactory init error", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("ConfigFactory close InputStream error", e);
                }
            }
        }
    }

    public static Properties getRedisConfig() {
        return redisConfig;
    }
}
