package com.servitization.webms.util;

import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.webms.entity.MetadataModule;

import java.util.List;

/**
 * 抽象主题角色
 */
interface Subject {
    /**
     * 注册观察者
     *
     * @param observer
     */
    void addObserver(String handlerName, Observer observer);

    /**
     * 删除观察者
     *
     * @param observer
     */
    void deleteObserver(Observer observer);

    /**
     * 创建元数据版本
     */
    void builderXml(long metadataId, String handlerName, MetadataModule module, List<ChainElementDefine> chainList);

    /**
     * 删除转发配置，通知所有观察者
     */
    void deleteProxy(List<String> proxyIdList);

    /**
     * 删除元数据配置，通知所有观察者
     */
    void deleteMetadata(List<String> metadataIdList);

    /**
     * 复制元数据操作
     */
    void copyMetadata(long oldMetaId, long newMetaId, long newProxyId, long oldProxyId);
}
