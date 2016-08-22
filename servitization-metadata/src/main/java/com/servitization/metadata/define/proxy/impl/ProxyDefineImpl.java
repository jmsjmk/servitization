package com.servitization.metadata.define.proxy.impl;

import com.servitization.metadata.define.embedder.impl.ChainHandlerDefineImpl;
import com.servitization.metadata.define.proxy.ProxyDefine;
import com.servitization.metadata.define.proxy.ServiceModule;
import com.servitization.metadata.define.proxy.ServicePool;
import com.servitization.metadata.util.ServiceModuleUtil;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProxyDefineImpl extends ChainHandlerDefineImpl implements ProxyDefine {

    private static final long serialVersionUID = 1L;
    private Set<String> resConvertServiceNames;
    private List<ServiceModule> serviceModuleList;
    private List<ServicePool> servicePoolList;

    @Override
    public List<ServiceModule> getServiceModuleList() {
        return serviceModuleList;
    }

    public void setServiceModuleList(List<ServiceModule> serviceModuleList) {
        this.serviceModuleList = serviceModuleList;
    }

    @Override
    public List<ServicePool> getServicePoolList() {
        return servicePoolList;
    }

    public void setServicePoolList(List<ServicePool> servicePoolList) {
        this.servicePoolList = servicePoolList;
    }

    @Override
    public Set<String> getResConvertServiceNames() {
        return resConvertServiceNames;
    }

    @Override
    public void serialize(Element parent) {
        Element me = parent.addElement(this.getClass().getName());
        me.addAttribute("name", getName());
        me.addAttribute("handlerClazz", getHandlerClazz());
        if (serviceModuleList != null && serviceModuleList.size() > 0) {
            Element e_serviceModuleList = me.addElement("serviceModuleList");
            for (ServiceModule sm : serviceModuleList) {
                sm.serialize(e_serviceModuleList);
            }
        }
        if (servicePoolList != null && servicePoolList.size() > 0) {
            Element e_servicePoolList = me.addElement("servicePoolList");
            for (ServicePool sp : servicePoolList) {
                sp.serialize(e_servicePoolList);
            }
        }
    }

    @Override
    public void deserialize(Element self) {
        this.setName(self.attributeValue("name"));
        this.setHandlerClazz(self.attributeValue("handlerClazz"));
        Element e_serviceModuleList = self.element("serviceModuleList");
        if (e_serviceModuleList != null) {
            serviceModuleList = new ArrayList<>();
            resConvertServiceNames = new HashSet<>();
            List<Element> entries = e_serviceModuleList.elements();
            if (entries != null && entries.size() > 0) {
                for (Element e_entry : entries) {
                    ServiceModule sm = new ServiceModule();
                    sm.deserialize(e_entry);
                    if (ServiceModuleUtil.isResConvertServiceModule(sm)) {
                        resConvertServiceNames.add(sm.getSourceService().getPath());
                    }
                    serviceModuleList.add(sm);
                }
            }
        }
        Element e_servicePoolList = self.element("servicePoolList");
        if (e_servicePoolList != null) {
            servicePoolList = new ArrayList<>();
            List<Element> entries = e_servicePoolList.elements();
            if (entries != null && entries.size() > 0) {
                for (Element e_entry : entries) {
                    ServicePool sp = new ServicePool();
                    sp.deserialize(e_entry);
                    servicePoolList.add(sp);
                }
            }
        }
    }
}
