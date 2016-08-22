package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataProxy;

import java.util.List;
import java.util.Map;

public interface MetadataProxyMapper {

    @DataSource(value = "slave")
    List<MetadataProxy> getMetadataProxyList(long metadataId);

    @DataSource(value = "slave")
    MetadataProxy getProxyById(long proxyId);

    @DataSource(value = "slave")
    List<MetadataProxy> getMetadataProxyListByParams(Map<String, Object> params);

    @DataSource(value = "slave")
    int getMetadataProxyCount(Map<String, Object> params);

    @DataSource(value = "master")
    int deleteMetadataProxys(List<String> ids);

    @DataSource(value = "master")
    int addMetadataProxy(MetadataProxy metadataProxy);

    @DataSource(value = "master")
    int updateMetadataProxy(MetadataProxy metadataProxy);

    @DataSource(value = "slave")
    List<String> selectServiceNameByServicePoolName(Map<String, Object> param);

    @DataSource(value = "master")
    int updateProxyPoolName(Map<String, Object> param);

    @DataSource(value = "slave")
    int sourcesUrlIsAgain(Map<String, Object> params);

    @DataSource(value = "master")
    void deleteProxyByMetadataid(List<String> args);
}
