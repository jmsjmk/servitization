package com.servitization.webms.service;

import com.servitization.webms.entity.MetadataPublish;
import com.servitization.webms.entity.MetadataPublishIp;

import java.util.List;
import java.util.Map;

public interface IMetadataPublishService {
    int publish(int nodeId, long nodeRelationId, long metadataId, long versionId);

    int getPublishedVersionIdByRelationId(long nodeRelationId);

    void updataPublishState();

    List<MetadataPublish> getPublishHistoryByNodeRelationId(Map params);

    int getPublishHistoryCount(Map<String, Object> params);

    List<MetadataPublishIp> getPublishDetail(long publishId);

    int publishNew(String versionId, String metadataId);
}
