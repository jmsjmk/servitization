package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataSession;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MetadataSessionMapper {

    @DataSource(value = "slave")
    List<MetadataSession> getMetadataSessionList(Map<String, Object> params);

    @DataSource(value = "slave")
    List<MetadataSession> getMetadataSessionListByMetaIdAndProxyId(@Param("metadataId") long metadataId,
                                                                   @Param("proxyId") long proxyId);

    @DataSource(value = "slave")
    int getMetadataSessionCount(Map<String, Object> params);

    @DataSource(value = "master")
    int deleteMetadataSessions(List<String> ids);

    @DataSource(value = "master")
    int addMetadataSession(MetadataSession metadataSession);

    @DataSource(value = "slave")
    MetadataSession getSessionById(long SessionId);

    @DataSource(value = "master")
    int updateMetadataSession(MetadataSession metadataSession);

    @DataSource(value = "slave")
    int vertifySession(Map<String, Object> params);

    @DataSource(value = "master")
    void deleteSessionByMetadataid(List<String> args);
}
