package com.servitization.webms.service;

import com.servitization.metadata.define.embedder.ChainElementDefine;
import com.servitization.webms.entity.Chain;
import com.servitization.webms.entity.Metadata;
import com.servitization.webms.util.ConcreteSubject;

import java.util.List;
import java.util.Map;

public interface IMetadataService {

    List<Metadata> getMetadataList(Map<String, Integer> params);

    int getMetadataCount();

    int deleteMetadatas(List<String> ids);

    int addMetadata(Metadata metadata);

    int isExistMetadata(Metadata metadata);

    List<Chain> handleChain(String chain);

    Metadata getMetadataById(Long metadataId);

    int updateMetadataChain(Metadata metadata);

    /**
     * 判断链条内添加的模块是否有重复
     *
     * @param chain
     * @return
     */
    boolean isCurrentChain(String chain);

    /**
     * 构造xml数据对象
     *
     * @param concreteSubject
     * @param metadataId
     * @param chainStr
     * @return
     */
    List<ChainElementDefine> chainList(ConcreteSubject concreteSubject, long metadataId, String chainStr);

    /**
     * 照着已有的元数据标识复制添加一份元数据
     *
     * @param metadata
     * @param beCopiedMetaId
     * @return
     */
    int addMetadataByCopy(Metadata metadata, String beCopiedMetaId);
}
