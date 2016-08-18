package com.servitization.webms.util.adapter;

import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.webms.entity.MetadataModule;
import com.servitization.webms.util.Observer;

import java.util.List;

/**
 * 抽象观察者适配器
 */
public abstract class BaseObserver implements Observer {

    @Override
    public void deleteMetadata(List<String> metadataId) {

    }

    @Override
    public void deleteProxy(List<String> proxyIdList) {

    }

    @Override
    public void builderXml(long metadataId, MetadataModule module, List<ChainElementDefine> chainList) {

    }

    @Override
    public void copyMetadata(long oldMetaId, long newMetaId, long newProxyId, long oldProxyId) {

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj.getClass());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
