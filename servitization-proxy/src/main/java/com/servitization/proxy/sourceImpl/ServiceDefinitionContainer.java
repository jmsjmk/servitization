package com.servitization.proxy.sourceImpl;

import com.servitization.commons.socket.client.RemoteClient;
import com.servitization.commons.socket.client.pool.ConnectPool;
import com.servitization.commons.socket.client.pool.IConnectPool;
import com.servitization.embedder.context.GlobalContext;
import com.servitization.embedder.immobile.ImmobileRequest;
import com.servitization.metadata.define.proxy.*;
import com.servitization.proxy.CommonLogger;
import com.servitization.proxy.IServiceMapping;
import com.servitization.proxy.IValveController;
import com.servitization.proxy.check.ProxyCheck;
import com.servitization.proxy.valve.SimpleValve;
import com.servitization.proxy.valve.UserValve;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceDefinitionContainer implements IServiceMapping {

    private ProxyDefine proxyDefine;

    private Map<String, ServiceModule> serviceMap;

    private Map<String, ServicePool> servicePoolMap;

    private Map<TargetService, IValveController> valves = new ConcurrentHashMap<>();

    private RemoteClient remoteClient;

    public ServiceDefinitionContainer(ProxyDefine pd, GlobalContext ctx) {
        proxyDefine = pd;
        CommonLogger.getLogger().info(
                String.format(
                        "ServiceDefinitionContainer with ConfLoader class %s",
                        proxyDefine == null ? null : proxyDefine.getClass()
                                .getName()));
        load();
    }

    private void load() {
        // 初始化池
        if (proxyDefine.getServicePoolList() != null && proxyDefine.getServicePoolList().size() > 0) {
            Map<String, ServicePool> tempServicePoolMap = new ConcurrentHashMap<>();
            Map<String, IConnectPool> connectPools = new HashMap<>();
            for (ServicePool sp : proxyDefine.getServicePoolList()) {
                if (StringUtils.isBlank(sp.getServicePoolName())) continue;
                // 仅 emcf 需初始化连接池
                // 一期项目只做http的代理 end
                if (sp.getServiceType() == 1) {
                    ConnectPool cp = new ConnectPool(sp.getServicePoolName(),
                            sp.getUrl(), sp.getCoefficient(),
                            sp.isForceCloseChannel(),
                            sp.getForceCloseTimeMillis());
                    connectPools.put(sp.getServicePoolName(), cp);
                }
                tempServicePoolMap.put(sp.getServicePoolName(), sp);
            }
            remoteClient = new RemoteClient();
            remoteClient.setConnectPools(connectPools);
            this.servicePoolMap = tempServicePoolMap;
            CommonLogger.getLogger().info("ServiceDefinitionContainer load init connectPools");
        }

        // 初始化module
        List<ServiceModule> serviceModules = proxyDefine.getServiceModuleList();
        Map<String, ServiceModule> tempServiceMap = new ConcurrentHashMap<>();
        if (serviceModules != null && !serviceModules.isEmpty()) {
            for (ServiceModule serviceModule : serviceModules) {
                SourceService srcServ = serviceModule.getSourceService();
                if (StringUtils.isBlank(srcServ.getPath())) continue;
                TargetService tagServ = serviceModule.getTargetService();
                // ---- 为目标服务(TargetService)建立 流量阀(Valve) 对象 ---- //
                if (tagServ.getThresholdType() == ThresholdType.BYPERCENTAGE) {
                    valves.put(tagServ, new SimpleValve(tagServ.getThreshold()));
                } else if (tagServ.getThresholdType() == ThresholdType.BYUSER) {
                    valves.put(tagServ, new UserValve(tagServ.getThreshold()));
                }
                tempServiceMap.put(
                        ProxyCheck.sourceServicePathCheck(srcServ.getPath()),
                        serviceModule);
            }
        }
        this.serviceMap = tempServiceMap;
        CommonLogger.getLogger().info("ServiceDefinitionContainer load serviceMapping");
    }

    public ServiceModule getServiceMappingInfo(ImmobileRequest request) {
        return serviceMap.get(request.getServiceName());
    }

    public ServiceModule getServiceMappingInfo(String key) {
        return serviceMap.get(key);
    }

    public List<ServiceModule> getServiceMappingInfos(String... keys) {
        return null;
    }

    public TargetService getTargetService(String key) {
        ServiceModule serviceModule = getServiceMappingInfo(key);
        return serviceModule == null ? null : serviceModule.getTargetService();
    }

    public TargetService getTargetService(ImmobileRequest request) {
        ServiceModule serviceModule = getServiceMappingInfo(request);
        return serviceModule == null ? null : serviceModule.getTargetService();
    }

    public IValveController getValve(TargetService ts) {
        return valves.get(ts);
    }

    public RemoteClient getRemoteClient() {
        return remoteClient;
    }

    public ServicePool getServicePoolInfo(String servicePoolName) {
        if (this.servicePoolMap == null || this.servicePoolMap.isEmpty())
            return null;
        return this.servicePoolMap.get(servicePoolName);
    }

    public void destory() {
        serviceMap = null;
        valves = null;
        remoteClient = null;
    }

    @Override
    public boolean isResConvertService(String serviceName) {
        return proxyDefine.getResConvertServiceNames().contains(serviceName);
    }
}
