package com.servitization.webms.service.impl;

import com.servitization.webms.entity.MetadataServicePool;
import com.servitization.webms.mapper.MetadataServicePoolMapper;
import com.servitization.webms.service.IMetadataServicePoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MetadataServicePoolServiceImpl implements IMetadataServicePoolService {

    @Resource
    private MetadataServicePoolMapper metadataServicePoolMapper;

    @Override
    public List<MetadataServicePool> selectPools(long metadataId) {
        return metadataServicePoolMapper.selectPools(metadataId);
    }

    @Override
    public int insertPool(MetadataServicePool pool) {
        return metadataServicePoolMapper.insertPool(pool);
    }

    @Override
    public int updatePoolByName(MetadataServicePool pool) {
        return metadataServicePoolMapper.updatePoolByName(pool);
    }

    @Override
    public int deletePoolByName(Map<String, Object> param) {
        return metadataServicePoolMapper.deletePoolByName(param);
    }

    @Override
    public List<MetadataServicePool> selectPoolByName(Map<String, Object> param) {
        return metadataServicePoolMapper.selectPoolByName(param);
    }

    @Override
    public void deleteServicePoolByMetadataid(List<String> metadataId) {
        metadataServicePoolMapper.deleteServicePoolByMetadataid(metadataId);
    }
}