package com.servitization.webms.util;

import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.webms.entity.MetadataModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 具体主题角色
 */
public class ConcreteSubject implements Subject {

    private static final Logger LOG = LoggerFactory.getLogger(ConcreteSubject.class);

    private Map<String, Observer> observerMap = new HashMap<>();

    private ConcreteSubject() {
    }

    public static ConcreteSubject instances() {
        return SingleHold.newInstances();
    }

    @Override
    public void deleteObserver(Observer observer) {

    }

    @Override
    public void addObserver(String handlerName, Observer observer) {
        if (!StringUtils.isBlank(handlerName)) {
            observerMap.put(handlerName, observer);
        }
    }

    @Override
    public void builderXml(long metadataId, String handlerName, MetadataModule module,
                           List<ChainElementDefine> chainList) {
        if (observerMap.containsKey(handlerName)) {
            observerMap.get(handlerName).builderXml(metadataId, module, chainList);
        }
    }

    @Override
    public void deleteProxy(List<String> proxyIdList) {
        Iterator<Observer> iterator = observerMap.values().iterator();
        while (iterator.hasNext()) {
            try {
                iterator.next().deleteProxy(proxyIdList);
            } catch (Exception e) {
                LOG.error("delete proxy exception:" + e);
            }
        }
    }

    @Override
    public void deleteMetadata(List<String> metadataIdList) {
        /**
         * 通知所有观察者，执行相应的事件
         */
        Iterator<Observer> iterator = observerMap.values().iterator();
        while (iterator.hasNext()) {
            try {
                iterator.next().deleteMetadata(metadataIdList);
            } catch (Exception e) {
                LOG.error("delete metadata exception:" + e);
            }
        }
    }

    @Override
    public void copyMetadata(long oldMetaId, long newMetaId, long newProxyId, long oldProxyId) {
        Iterator<Observer> iterator = observerMap.values().iterator();
        while (iterator.hasNext()) {
            try {
                iterator.next().copyMetadata(oldMetaId, newMetaId, newProxyId, oldProxyId);
            } catch (Exception e) {
                LOG.error("copyMetadata  exception:" + e);
            }
        }
    }

    private static class SingleHold {
        private static ConcreteSubject single = new ConcreteSubject();

        public static ConcreteSubject newInstances() {
            return SingleHold.single;
        }
    }
}