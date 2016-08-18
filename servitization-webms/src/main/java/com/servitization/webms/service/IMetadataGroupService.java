package com.servitization.webms.service;

import com.servitization.webms.entity.MetadataGroup;

import java.util.List;

public interface IMetadataGroupService {

    List<MetadataGroup> getGroupsByIds(List<String> ids);

    List<MetadataGroup> getGroupsByMetadataId(Long metadataId);

    MetadataGroup getGroupById(Long groupId);

    int addGroup(MetadataGroup metadataGroup);

    int updateGroup(MetadataGroup metadataGroup);

    int deleteGroup(long groupId);

    void deleteGroupByMetadataid(List<String> args);
}
