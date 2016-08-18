package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataPublish;

import java.util.List;
import java.util.Map;

public interface MetadataPublishMapper {

    @DataSource(value = "master")
    int addPublish(MetadataPublish metadataPublish);

    @DataSource(value = "slave")
    Integer getPublishedVersionIdByRelationId(long nodeRelationId);

    @DataSource(value = "slave")
    MetadataPublish getPublishingJob();

    @DataSource(value = "master")
    int updatePublishStatus(MetadataPublish publish);

    @DataSource(value = "slave")
    List<MetadataPublish> getPublishHistoryByNodeRelationId(Map params);

    @DataSource(value = "slave")
    int getPublishHistoryCount(Map<String, Object> params);

    @DataSource(value = "master")
    void deletePublicData();
}
