package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataPublishIp;

import java.util.List;

public interface MetadataPublishIpMapper {

    @DataSource(value = "master")
    int addPublishIp(MetadataPublishIp metadataPublishIp);

    @DataSource(value = "slave")
    List<MetadataPublishIp> getPublishIpsByJobId(long publishId);

    @DataSource(value = "master")
    int batchUpdateStatus(List<MetadataPublishIp> metadataPublishIps);

    @DataSource(value = "master")
    void deletePublicIp();
}
