package com.servitization.web.define.proxy.impl;

import com.servitization.web.define.proxy.IServiceConfLoader;
import com.servitization.web.define.proxy.ServiceModule;

import java.util.List;

/**
 * bean name serviceConfLoader
 */
public class ServiceConfLoader implements IServiceConfLoader {
    private List<ServiceModule> serviceModuleList;

    public List<ServiceModule> getServiceModuleList() {
        return serviceModuleList;
    }

    public void setServiceModuleList(List<ServiceModule> serviceModuleList) {
        this.serviceModuleList = serviceModuleList;
    }

    public List<ServiceModule> loadServiceConf() {
        return serviceModuleList;
    }

    public List<ServiceModule> loadServiceConf(int mod) {
        return serviceModuleList;
    }

    public int getMax_Proxy_Index() {
        return 10;
    }
}
