package com.servitization.commons.business.agent;

import java.lang.reflect.Proxy;
import java.util.Properties;

public class AgentSupport {

    protected Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * 代理接口，接口无需写实现类.
     *
     * @param agentInterface
     * @param <T>
     * @return
     */
    public <T> T createBean(Class<T> agentInterface) {
        ClassLoader classLoader = agentInterface.getClassLoader();
        Class<?>[] interfaces = new Class[]{agentInterface};
        AgentProxy proxy = new AgentProxy();
        proxy.setProperties(properties);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }
}
