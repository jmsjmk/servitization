package com.servitization.commons.socket.spring.remote;

import com.servitization.commons.socket.remote.RemoteSupport;
import org.springframework.beans.factory.FactoryBean;

public class RemoteFactroyBean<T> extends RemoteSupport implements FactoryBean<T> {

    private Class<T> remoteInterface;

    public void setRemoteInterface(Class<T> remoteInterface) {
        this.remoteInterface = remoteInterface;
    }

    @Override
    public T getObject() throws Exception {

        return super.createBean(remoteInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return this.remoteInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
