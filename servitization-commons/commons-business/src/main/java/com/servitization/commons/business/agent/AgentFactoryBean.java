package com.servitization.commons.business.agent;

import org.springframework.beans.factory.FactoryBean;

public class AgentFactoryBean<T> extends AgentSupport implements FactoryBean<T> {

    private Class<T> agentInterface;

    public void setAgentInterface(Class<T> agentInterface) {
        this.agentInterface = agentInterface;
    }

    @Override
    public T getObject() throws Exception {
        return super.createBean(agentInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return this.agentInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
