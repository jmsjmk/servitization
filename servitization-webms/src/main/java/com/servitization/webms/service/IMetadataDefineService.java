package com.servitization.webms.service;

import com.servitization.webms.entity.MetadataDefine;

import java.util.List;
import java.util.Map;

public interface IMetadataDefineService {

    List<MetadataDefine> getMetadataDefineListByMetaIdAndProxyId(long metadataId, long proxyId);

    List<MetadataDefine> getMetadataDefineList(Map<String, Object> params);

    int getMetadataDefineCount(Map<String, Object> params);

    int deleteMetadataDefines(List<String> ids);

    int addMetadataDefine(MetadataDefine metadataDefine);

    MetadataDefine getDefineById(long id);

    int updateMetadataDefine(MetadataDefine metadataDefine);

    int vertifyDefine(Map<String, Object> params);

    int deleteDefineByMetadaId(List<String> args);

    String getDefineWhitelist(Map<String, Object> params);

    int addDefineWhitelist(Map<String, Object> params);

    int updateDefineWhitelist(Map<String, Object> params);

    int deleteDefineWhitelist(List<String> ids);
}
