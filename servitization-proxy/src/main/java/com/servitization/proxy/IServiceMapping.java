package com.servitization.proxy;

import com.servitization.commons.socket.client.RemoteClient;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.metadata.define.proxy.ServiceModule;
import com.servitization.metadata.define.proxy.ServicePool;
import com.servitization.metadata.define.proxy.TargetService;

import java.util.List;

public interface IServiceMapping {

    ServiceModule getServiceMappingInfo(ImmobileRequest request);

    ServiceModule getServiceMappingInfo(String key);

    List<ServiceModule> getServiceMappingInfos(String... keys);

    TargetService getTargetService(String key);

    TargetService getTargetService(ImmobileRequest request);

    IValveController getValve(TargetService ts);

    RemoteClient getRemoteClient();

    ServicePool getServicePoolInfo(String servicePoolName);

    void destory();

    boolean isResConvertService(String serviceName);
}
