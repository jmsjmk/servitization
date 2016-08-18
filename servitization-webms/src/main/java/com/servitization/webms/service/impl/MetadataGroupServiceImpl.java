package com.servitization.webms.service.impl;

import com.servitization.webms.entity.MetadataGroup;
import com.servitization.webms.mapper.MetadataGroupMapper;
import com.servitization.webms.service.IMetadataGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MetadataGroupServiceImpl implements IMetadataGroupService {

    @Resource
    private MetadataGroupMapper metadataGroupMapper;

    @Override
    public List<MetadataGroup> getGroupsByIds(List<String> ids) {
        return metadataGroupMapper.getGroupsByIds(ids);
    }

    @Override
    public List<MetadataGroup> getGroupsByMetadataId(Long metadataId) {
        return metadataGroupMapper.getGroupsByMetadataId(metadataId);
    }

    @Override
    public MetadataGroup getGroupById(Long groupId) {
        return metadataGroupMapper.getGroupById(groupId);
    }

    @Override
    public int addGroup(MetadataGroup metadataGroup) {
        return metadataGroupMapper.addGroup(metadataGroup);
    }

    @Override
    public int updateGroup(MetadataGroup metadataGroup) {
        return metadataGroupMapper.updateGroup(metadataGroup);
    }

    @Override
    public int deleteGroup(long groupId) {
        return metadataGroupMapper.deleteGroup(groupId);
    }

    @Override
    public void deleteGroupByMetadataid(List<String> args) {
        metadataGroupMapper.deleteGroupByMetadataid(args);
    }
}
