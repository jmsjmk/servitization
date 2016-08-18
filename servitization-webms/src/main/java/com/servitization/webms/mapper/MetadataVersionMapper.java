package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataVersion;
import com.servitization.webms.entity.MetadataXml;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MetadataVersionMapper {

    @DataSource(value = "slave")
    List<MetadataVersion> getMetadataVersionList(Map<String, Object> params);

    @DataSource(value = "slave")
    int getMetadataVersionCount(Map<String, Object> params);

    @DataSource(value = "master")
    int addVersion(MetadataVersion version);

    @DataSource(value = "slave")
    MetadataVersion getMetadataVersionById(long id);

    @DataSource(value = "slave")
    MetadataVersion getLatestMetadataVersionByMetadataId(long metadataId);

    @DataSource(value = "slave")
    List<MetadataVersion> getMetadataVersionListByPage(@Param("pageIndex") int pageIndex,
                                                       @Param("pageSize") int pageSize);

    @DataSource(value = "master")
    int addMetadataXml(MetadataXml metadataXml);

    @DataSource(value = "slave")
    MetadataXml getMetadataXmlByVersionId(@Param("id") long id);

    @DataSource(value = "slave")
    String getMetadataXmlStrByVersionId(@Param("id") long id);

    @DataSource(value = "master")
    void deleteVersionByMetadataid(List<String> metadataId);

    @DataSource(value = "master")
    void deleteXmlVersion();
}
