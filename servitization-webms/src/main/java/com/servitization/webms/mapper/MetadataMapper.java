package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.Metadata;

import java.util.List;
import java.util.Map;

public interface MetadataMapper {

    @DataSource(value = "slave")
    List<Metadata> getMetadataList(Map<String, Integer> params);

    @DataSource(value = "slave")
    int getMetadataCount();

    @DataSource(value = "master")
    int deleteMetadatas(List<String> ids);

    @DataSource(value = "master")
    int addMetadata(Metadata metadata);

    @DataSource(value = "slave")
    int isExistMetadata(Metadata metadata);

    @DataSource(value = "slave")
    Metadata getMetadataById(Long metadataId);

    @DataSource(value = "master")
    int updateMetadataChain(Metadata metadata);
}
