package com.servitization.webms.service.impl;

import com.servitization.webms.mapper.MetadataAesMapper;
import com.servitization.webms.service.IMetadataAesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MetadataAesServiceImpl implements IMetadataAesService {

    @Resource
    private MetadataAesMapper metadataAesMapper;

    @Override
    public String getAesWhitelist(Map<String, Object> params) {
        return metadataAesMapper.getAesWhitelist(params);
    }

    @Override
    public int addAesWhitelist(Map<String, Object> params) {
        return metadataAesMapper.addAesWhitelist(params);
    }

    @Override
    public int updateAesWhitelist(Map<String, Object> params) {
        return metadataAesMapper.updateAesWhitelist(params);
    }

    @Override
    public int deleteAesWhitelist(List<String> ids) {
        return metadataAesMapper.deleteAesWhitelist(ids);
    }
}
