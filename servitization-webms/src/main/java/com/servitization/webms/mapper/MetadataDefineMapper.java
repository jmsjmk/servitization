package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataDefine;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MetadataDefineMapper {

    @DataSource(value = "slave")
    List<MetadataDefine> getMetadataDefineList(Map<String, Object> params);

    @DataSource(value = "slave")
    List<MetadataDefine> getMetadataDefineListByMetaIdAndProxyId(@Param("metadataId") long metadataId,
                                                                 @Param("proxyId") long proxyId);

    @DataSource(value = "slave")
    int getMetadataDefineCount(Map<String, Object> params);

    @DataSource(value = "master")
    int deleteMetadataDefines(List<String> ids);

    @DataSource(value = "master")
    int addMetadataDefine(MetadataDefine metadataDefine);

    @DataSource(value = "slave")
    MetadataDefine getDefineById(long defineId);

    @DataSource(value = "master")
    int updateMetadataDefine(MetadataDefine metadataDefine);

    @DataSource(value = "slave")
    int vertifyDefine(Map<String, Object> params);

    @DataSource(value = "master")
    int deleteDefineByMetadaId(List<String> args);

    @DataSource(value = "slave")
    String getDefineWhitelist(Map<String, Object> params);

    @DataSource(value = "master")
    int addDefineWhitelist(Map<String, Object> params);

    @DataSource(value = "master")
    int updateDefineWhitelist(Map<String, Object> params);

    @DataSource(value = "master")
    int deleteDefineWhitelist(List<String> ids);
}
