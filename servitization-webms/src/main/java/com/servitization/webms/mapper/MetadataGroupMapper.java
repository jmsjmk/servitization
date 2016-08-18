package com.servitization.webms.mapper;

import com.servitization.commons.db.DataSource;
import com.servitization.webms.entity.MetadataGroup;

import java.util.List;

public interface MetadataGroupMapper {

    @DataSource(value = "slave")
    List<MetadataGroup> getGroupsByIds(List<String> ids);

    @DataSource(value = "slave")
    List<MetadataGroup> getGroupsByMetadataId(Long metadataId);

    @DataSource(value = "slave")
    MetadataGroup getGroupById(Long groupId);

    @DataSource(value = "master")
    int addGroup(MetadataGroup metadataGroup);

    @DataSource(value = "master")
    int updateGroup(MetadataGroup metadataGroup);

    @DataSource(value = "master")
    int deleteGroup(long groupId);

    @DataSource(value = "master")
    void deleteGroupByMetadataid(List<String> args);
}
