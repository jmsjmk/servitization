package com.servitization.webms.service;

import com.servitization.webms.entity.MetadataProxy;

import java.util.List;
import java.util.Map;

public interface IMetadataProxyService {

    List<MetadataProxy> getMetadataProxyList(long metadataId);

    MetadataProxy getProxyById(long proxyId);

    List<MetadataProxy> getMetadataProxyListByParams(Map<String, Object> params);

    int getMetadataProxyCount(Map<String, Object> params);

    int deleteMetadataProxys(List<String> ids);

    int addMetadataProxy(MetadataProxy metadataProxy);

    int updateMetadataProxy(MetadataProxy metadataProxy);

    List<String> selectServiceNameByServicePoolName(Map<String, Object> param);

    int updateProxyPoolName(Map<String, Object> param);

    int sourcesUrlIsAgain(Map<String, Object> params);

    void deleteProxyByMetadataid(List<String> args);

}
