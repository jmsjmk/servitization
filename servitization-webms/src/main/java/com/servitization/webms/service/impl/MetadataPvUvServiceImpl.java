package com.servitization.webms.service.impl;

import com.servitization.webms.entity.MetadataPvUv;
import com.servitization.webms.mapper.MetadataPvUvMapper;
import com.servitization.webms.service.IMetadataPvUvService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MetadataPvUvServiceImpl implements IMetadataPvUvService {

    @Resource
    private MetadataPvUvMapper metadataPvUvMapper;

    @Override
    public List<MetadataPvUv> getMetadataPvUvList(Map<String, Object> params) {
        return metadataPvUvMapper.getMetadataPvUvList(params);
    }

    @Override
    public int getMetadataPvUvCount(Map<String, Object> params) {
        return metadataPvUvMapper.getMetadataPvUvCount(params);
    }

    @Override
    public int vertifyPvUv(Map<String, Object> params) {
        return metadataPvUvMapper.vertifyPvUv(params);
    }

    @Override
    public String addPvUv(Map<String, Object> params) {
        int count = metadataPvUvMapper.addPvUv(params);
        return String.valueOf(count);
    }

    @Override
    public int delete(Map<String, Object> params) {
        return metadataPvUvMapper.delete(params);
    }

    @Override
    public int deleteMany(List<String> list) {
        int count = metadataPvUvMapper.deleteMany(list);
        return count;
    }

    @Override
    public void deletePvUvByMetadataid(List<String> args) {
        metadataPvUvMapper.deletePvUvByMetadataid(args);
    }
}
