package com.servitization.commons.socket.remote;

import com.servitization.commons.socket.client.RemoteClient;

import java.lang.reflect.Proxy;

public class RemoteSupport {

    private RemoteClient remoteClient;

    /**
     * 代理接口，接口无需写实现类.
     *
     * @param <T>
     * @param remoteInterface
     * @return
     */
    public <T> T createBean(Class<T> remoteInterface) {
        ClassLoader classLoader = remoteInterface.getClassLoader();
        Class<?>[] interfaces = new Class[]{remoteInterface};
        RemoteProxy proxy = new RemoteProxy();
        proxy.setRemoteClient(remoteClient);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }

    public RemoteClient getRemoteClient() {
        return remoteClient;
    }

    public void setRemoteClient(RemoteClient remoteClient) {
        this.remoteClient = remoteClient;
    }

}
