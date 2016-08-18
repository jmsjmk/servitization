package com.servitization.web.service;

import com.servitization.web.callback.IResourceChangeCallback;
import com.servitization.web.common.CommonsUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

public class ResourceContext {
    private static final Logger Logger = LoggerFactory.getLogger(ResourceContext.class);

    private ApplicationContext applicationContext;

    private static IResourceChangeCallback resourceChangeCallback;

    public static final Properties CONFIG_PROVIDAR = CommonsUtil.loadProperties("config/config.properties");

    public static IResourceChangeCallback getResourceChangeCallback() {
        return resourceChangeCallback;
    }

    public static void setResourceChangeCallback(IResourceChangeCallback resourceChangeCallback) {
        ResourceContext.resourceChangeCallback = resourceChangeCallback;
    }

    public ResourceContext() {
        initResourceContext();
    }

    //初始化servitization的SpringContext
    private void initResourceContext() {
        if (CONFIG_PROVIDAR == null || StringUtils.isBlank(CONFIG_PROVIDAR.getProperty("servitization_resource_context_path")))
            throw new RuntimeException("servitization-resource-context.xml can not find!");
        try {
            applicationContext = new ClassPathXmlApplicationContext(CONFIG_PROVIDAR.getProperty("servitization_resource_context_path"));
        } catch (Exception e) {
            Logger.error("initResourceContext erro:", e);
            throw new RuntimeException(e);
        }
    }

    //获取具体resource
    public Object getValue(String key) {
        return applicationContext.getBean(key);
    }

    public <T> T getValue(String key, Class<T> requiredType) {
        return applicationContext.getBean(key, requiredType);
    }

    public <T> T getValue(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }
}
