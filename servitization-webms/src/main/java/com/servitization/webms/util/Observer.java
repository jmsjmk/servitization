package com.servitization.webms.util;

import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.webms.entity.MetadataModule;

import java.util.List;

/**
 * 抽象观察者
 */
public interface Observer {
    /**
     * 当删除元数据的配置时候，需要把其他表中对应当下要删除的元数据的所有数据删除掉
     *
     * @param metadataId
     */
    void deleteMetadata(List<String> metadataId);

    /**
     * 删除转发配置的时候，需要把其他表中含有的转发配置相应的删除
     *
     * @param proxyIdList
     */
    void deleteProxy(List<String> proxyIdList);

    /**
     * 创建元数据版本
     *
     * @param module
     * @return
     */
    void builderXml(long metadataId, MetadataModule module, List<ChainElementDefine> chainList);

    /**
     * 模块的handler名字(和管理界面模块管理中的处理模块名相同)，主要根据该名字来通知观察者执行相应的事件处理
     */
    String getHandlerName();

    /**
     * 复制元数据操作
     */
    void copyMetadata(long oldMetaId, long newMetaId, long newProxyId, long oldProxyId);
}